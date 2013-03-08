(ns lightandsound.systems.physics
  (:require [lightandsound.entities :as entities]
            [lightandsound.components :as components])
  (:use [lightandsound.systems :only [PSystem]]))

(defn add-velocity
  [position velocity]
  (components/position
   (+ (:x position) (:x velocity))
   (+ (:y position) (:y velocity))
   (+ (:z position) (:z velocity))))

(defn swap-velocity [entity]
  (let [id (first entity)
        comps (second entity)
        pos (entities/get-component comps :position)
        vel (entities/get-component comps :velocity)]
    [id (conj (entities/remove-component comps :position)
              (add-velocity pos vel))]))

;; maybe use multiple physics systems, for now just moves along velocity
(defrecord PhysicsSystem []
  PSystem
  (components [_] #{:position :velocity})
  (setup [_] nil)
  (run [_ globals ents]
    (into {} (map swap-velocity ents)))
)

(defn physics-system [] (PhysicsSystem.))