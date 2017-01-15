(ns slacker-htrace.main
  (:require [slacker.client :as sc]
            [slacker.server :as ss]
            [slacker.interceptor :as si]
            [slacker-htrace.core :as st]
            [slacker-htrace.api]
            [slacker-htrace.tracer :as tr]))

;; create slacker client with tracer interceptor configured
(def client (sc/slackerc "127.0.0.1:2334"
                         :interceptors (si/interceptors [(st/client-interceptor tr/tracer tr/tracer-extension-id)])))

(sc/defn-remote client expensive-operations
  :remote-ns "slacker-htrace.api")

(defn -main [& args]
  ;; create slacker server with tracer interceptor configured
  (let [server (ss/start-slacker-server (the-ns 'slacker-htrace.api) 2334
                                        :interceptors (si/interceptors [(st/server-interceptor tr/tracer tr/tracer-extension-id)]))]

    ;; call remote functions 50 times
    (dotimes [_ 50] (println (expensive-operations 2)))

    ;; close everything
    (sc/shutdown-slacker-client-factory)
    (ss/stop-slacker-server server)
    (shutdown-agents)))
