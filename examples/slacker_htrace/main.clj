(ns slacker-htrace.main
  (:require [slacker.client :as sc]
            [slacker.server :as ss]
            [slacker.interceptor :as si]
            [slacker-htrace.core :as st]
            [slacker-htrace.api]
            [slacker-htrace.tracer :as tr]))

(def client (sc/slackerc "127.0.0.1:2334"
                         :interceptors (si/interceptors [(st/client-interceptor tr/tracer tr/tracer-extension-id)])))

(sc/defn-remote client expensive-operations
  :remote-ns "slacker-htrace.api")

(defn -main [& args]
  (let [server (ss/start-slacker-server (the-ns 'slacker-htrace.api) 2334
                                        :interceptors (si/interceptors [(st/server-interceptor tr/tracer tr/tracer-extension-id)]))]

    (dotimes [_ 50] (println (expensive-operations 2)))

    ;; close everything
    (sc/shutdown-slacker-client-factory)
    (ss/stop-slacker-server server)
    (shutdown-agents)))
