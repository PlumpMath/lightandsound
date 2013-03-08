(ns lightandsound.systems.quanta
  (:require [lightandsound.entities :as entities]
            [lightandsound.components :as components])
  (:use [lightandsound.systems :only [PSystem]]))

;; Spawn at -1100 x
(defrecord QCreationSystem []
  PSystem
  (components [_] #{})
  (setup [_] nil)
  (run [_ globals ents]
    {(entities/gen-id)
     [(components/position -1100 0 0)
      (components/velocity 10 0 0)]})
)


(defn quanta-creation-system [] (QCreationSystem.))
