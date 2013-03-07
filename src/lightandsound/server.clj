(ns lightandsound.server
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.java.io :as io]))

(defroutes app
  (GET "/" [] (.openStream (io/resource "public/index.html")))
  (route/resources "/")
  (route/not-found "Page not found"))
