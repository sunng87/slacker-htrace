(defproject slacker-htrace "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[slacker "0.15.0-SNAPSHOT"]
                 [org.apache.htrace/htrace-core4 "4.2.0-incubating"]]
  :profiles {:example {:source-paths ["examples"]}
             :dev {:dependencies [[org.clojure/clojure "1.8.0"]]}}
  :deploy-repositories {"releases" :clojars}
  :aliases {"run-example" ["trampoline" "with-profile" "default,example" "run" "-m" "slacker-htrace.main"]})
