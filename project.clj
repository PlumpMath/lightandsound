(defproject lightandsound "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.0"]
                 [compojure "1.1.5"]]
  :plugins [[lein-cljsbuild "0.3.0"]
            [lein-ring "0.8.3"]]
  :repl-listen-port 9000
  :cljsbuild
  {:builds
   [{:source-paths ["src/lightandsound/app"],
     :compiler
     {:pretty-print true,
      :output-to "resources/public/js/lightandsound.js",
      :optimizations :whitespace}}]}
  :ring {:handler lightandsound.server/app})
