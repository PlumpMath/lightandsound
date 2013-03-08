(ns lightandsound.systems.graphics
  (:require [lightandsound.entities :as entities])
  (:use [lightandsound.systems :only [PSystem]]))

;; for now render everything as quanta
(defrecord GraphicsSystem [camera scene renderer quanta]
  PSystem
  (components [_] #{:position})
  (setup [_]
    (set! (.-z (.-position camera)) 1000)
    (.setSize renderer (.-innerWidth js/window) (.-innerHeight js/window))
    (.appendChild js/document.body (.-domElement renderer)))
  (run [_ globals ents]
    ;; Create any quantums that aren't already in the scene
    (let [current @quanta]
      (doseq [ent ents]
        (when (not (contains? current (first ent)))
          (let [new (js/THREE.Mesh.
                           (js/THREE.SphereGeometry.
                                           50
                                           16
                                           16)
                           (js/THREE.MeshLambertMaterial. (clj->js
                                                           {:color 13369344})))]
            (.add scene new)
            (swap! quanta assoc (first ent) new)))
        ;;todo update position
        (let [pos (.-position (get @quanta (first ent)))
              {:keys [x y z]} (entities/get-component (second ent) :position)]
          (aset pos "x" x)
          (aset pos "y" y)
          (aset pos "z" z)))
    (.render renderer scene camera))
    nil)
)

(defn graphics-system
  []
  (GraphicsSystem. (js/THREE.PerspectiveCamera.
                    75
                    (/ (.-innerWidth js/window)
                       (.-innerHeight js/window))
                    1
                    10000)
                   (js/THREE.Scene.)
                   (js/THREE.WebGLRenderer.)
                   (atom {})))
