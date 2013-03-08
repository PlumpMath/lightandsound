(ns lightandsound.systems.physics
  (:require [lightandsound.entities :as entities]
            [lightandsound.components :as components])
  (:use [lightandsound.systems :only [PSystem]]))

(defn add-velocity
  [position velocity td]
  (.log js/console td)
  (components/position
   (+ (:x position) (* (:x velocity) td))
   (+ (:y position) (* (:y velocity) td))
   (+ (:z position) (* (:z velocity) td))))

(defn swap-velocity [td entity]
  (let [id (first entity)
        comps (second entity)
        pos (entities/get-component comps :position)
        vel (entities/get-component comps :velocity)]
    [id (conj (entities/remove-component comps :position)
              (add-velocity pos vel td))]))

;; maybe use multiple physics systems, for now just moves along velocity
(defrecord PhysicsSystem []
  PSystem
  (components [_] #{:position :velocity})
  (setup [_] nil)
  (run [_ globals ents]
    (let [td (:delta globals)]
      (into {} (map (partial swap-velocity td) ents))))
)

(defn physics-system [] (PhysicsSystem.))