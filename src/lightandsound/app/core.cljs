(ns lightandsound.core
  (:require [lightandsound.entities :as ent]
            [lightandsound.components :as comp]
            [lightandsound.systems :as sys])
  (:use [lightandsound.systems.graphics :only [graphics-system]]
        [lightandsound.systems.quanta :only [quanta-creation-system]]
        [lightandsound.systems.physics :only [physics-system]]))

(defn get-global-values
  [] {})

;; Have to have this be global so it's in scope of callbacks.
(def entities (atom {:best-guy [(comp/position -1100 0 0)
                                (comp/velocity 10 0 0)]}))
(def systems [(graphics-system)
              (quanta-creation-system)
              (physics-system)
])

(defn animation-loop [time]
  (.webkitRequestAnimationFrame js/window animation-loop)
  ;; call all systems with relevant data.
  (doseq [s systems]
    (let [needed-components (sys/components s)
          needed-entities (ent/get-with-components @entities needed-components)
          globals (get-global-values)
          changed (sys/run s globals needed-entities)]
      (swap! entities ent/change-entities changed))))

(defn main []
  ;; setup systems
  (doseq [s systems]
    (sys/setup s))

  ;; main loop
  (animation-loop (.getTime (js/Date.))))

(main)