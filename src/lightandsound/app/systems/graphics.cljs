(ns lightandsound.systems.graphics
  (:use [lightandsound.systems :only [PSystem]]))

;; for now render everything as quanta
(defrecord GraphicsSystem [camera scene renderer quanta]
  PSystem
  (components [_] #{:position})
  (setup [_]
    (set! (.-z (.-position camera)) 1000)
    (.setSize renderer (.-innerWidth js/window) (.-innerHeight js/window))
    (.appendChild js/document.body (.-domElement renderer)))
  (run [_ globals entities]
    ;; Create any quantums that aren't already in the scene
    (let [current @quanta]
      (doseq [ent entities]
        (if (not (contains? current (first ent)))
          (let [new (js/THREE.Mesh.
                           (js/THREE.SphereGeometry.
                                           50
                                           16
                                           16)
                           (js/THREE.MeshLambertMaterial. (clj->js
                                                           {:color 13369344})))]
            (.add scene new)
            (swap! quanta assoc (first ent) new))
          ;;todo update position
          nil
          )))
    (.render renderer scene camera))
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
