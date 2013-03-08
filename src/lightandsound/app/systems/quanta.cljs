(ns lightandsound.systems.quanta
  (:require [lightandsound.entities :as entities]
            [lightandsound.components :as components])
  (:use [lightandsound.systems :only [PSystem]]))

;; Spawn at -1100 x
(defrecord QCreationSystem [schedule]
  PSystem
  (components [_] #{})
  (setup [_] nil)
  (run [_ globals _]
    ;; now lets make these spawn maybe once every 5 seconds?
    (let [time (:now globals)
          delta (:delta globals)]
      (when (> time @schedule)
        (.log js/console "schedule it yo")
        (reset! schedule (+ 5000 time))
        {(entities/gen-id)
         [(components/position -1100 0 0)
          (components/velocity 100 0 0)]})))
)

(defn quanta-creation-system [] (QCreationSystem. (atom 0)))
