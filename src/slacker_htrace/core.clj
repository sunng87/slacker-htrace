(ns slacker-htrace.core
  (:require [slacker.common :as s]
            [slacker.interceptor :as si])
  (:import [org.apache.htrace.core Tracer TraceScope SpanId]))

(defn- from-span-id [^SpanId sid]
  [(.getHigh sid) (.getLow sid)])

(defn- to-span-id [[high low]]
  (SpanId. high low))

(defn client-interceptor [^Tracer tracer span-id-extension-id]
  {:pre (fn [req]
          (let [scope-name (str (:fname req) ":outer")
                trace-scope (.newScope tracer scope-name)]
            (-> req
                (assoc ::trace-scope trace-scope)
                (update :extensions assoc
                        span-id-extension-id
                        (from-span-id(.getSpanId ^TraceScope trace-scope))))))
   :post (fn [resp]
           (when-let [scope (::trace-scope resp)]
             (.close ^TraceScope scope))
           (dissoc resp ::trace-scope))})

(defn server-interceptor [^Tracer tracer span-id-extension-id]
  {:before (fn [req]
             (let [scope-name (str (:fname req) ":inner")
                   span-id (-> req :extensions (get span-id-extension-id) to-span-id)
                   trace-scope (.newScope tracer scope-name span-id)]
               (-> req
                   (assoc ::trace-scope trace-scope))))
   :after (fn [resp]
            (when-let [scope (::trace-scope resp)]
              (.close ^TraceScope scope))
            (dissoc resp ::trace-scope))})
