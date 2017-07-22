(defproject slacker/slacker-htrace "0.1.0"
  :description "A htrace extension for slacker rpc"
  :url "http://github.com/sunng87/slacker-htrace"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[slacker "0.15.0"]
                 [org.apache.htrace/htrace-core4 "4.2.0-incubating"]]
  :profiles {:example {:source-paths ["examples"]}
             :zipkin-example {:dependencies [[org.apache.htrace/htrace-zipkin "4.2.0-incubating"]]}
             :dev {:dependencies [[org.clojure/clojure "1.8.0"]]}}
  :deploy-repositories {"releases" :clojars}
  :aliases {"run-example" ["trampoline" "with-profile" "default,example" "run" "-m" "slacker-htrace.main"]
            "run-zipkin-example" ["trampoline" "with-profile" "default,example,zipkin-example" "run" "-m" "slacker-htrace.main"]})
