(ns slacker-htrace.tracer
  (:import [org.apache.htrace.core Tracer$Builder StandardOutSpanReceiver
            HTraceConfiguration SpanId]))

(def htrace-config-map
  (try
    ;; try load zipkin receiver
    (import '[org.apache.htrace.impl ZipkinSpanReceiver])
    {"span.receiver.classes" "org.apache.htrace.impl.ZipkinSpanReceiver"
     "zipkin.scribe.hostname" "localhost"
     "zipkin.scribe.port" "9410"
     "sampler.classes" "org.apache.htrace.core.AlwaysSampler"}
    (catch Throwable _
      ;; fallback to stdout receiver
      {"span.receiver.classes" "org.apache.htrace.core.StandardOutSpanReceiver"
       "sampler.classes" "org.apache.htrace.core.AlwaysSampler"})))

(def config (HTraceConfiguration/fromMap htrace-config-map))
(def tracer (.. (Tracer$Builder. "test")
                (conf config)
                (build)))

(def tracer-extension-id 1)
