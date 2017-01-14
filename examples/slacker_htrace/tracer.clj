(ns slacker-htrace.tracer
  (:import [org.apache.htrace.core Tracer$Builder StandardOutSpanReceiver
            HTraceConfiguration SpanId]))

(def config (HTraceConfiguration/fromMap
             {"span.receiver.classes" "org.apache.htrace.core.StandardOutSpanReceiver"
              "sampler.classes" "org.apache.htrace.core.AlwaysSampler"}))
(def tracer (.. (Tracer$Builder. "test")
                (conf config)
                (build)))

(def tracer-extension-id 1)
