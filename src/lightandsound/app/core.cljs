(ns lightandsound.core
  (:require [lightandsound.entities :as ent]
            [lightandsound.components :as comp]
            [lightandsound.systems :as sys])
  (:use [lightandsound.systems.graphics :only [graphics-system]]))

(defn get-global-values
  [] {})

;; Have to have this be global so it's in scope of callbacks.
(def entities (atom {:best-quantum [(comp/position 1 1 1)]}))
(def systems [(graphics-system)])

(defn animation-loop []
  (.webkitRequestAnimationFrame js/window animation-loop)
  ;; call all systems with relevant data.
  (doseq [s systems]
    (let [needed-components (sys/components s)
          needed-entities (ent/get-with-components @entities needed-components)
          globals (get-global-values)
          changed (sys/run s globals needed-entities)]
      (swap! entities ent/change-entities changed)))
)

(defn main []
  ;; setup graphics

  ;; setup systems
  (doseq [s systems]
    (sys/setup s))

  ;; main loop
  (animation-loop))

(main)