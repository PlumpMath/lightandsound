(ns lightandsound.components)

(defn position
  [x y z]
  {:x x :y y :z z :type :position})

(defn velocity
  [x y z]
  {:x x :y y :z z :type :velocity})