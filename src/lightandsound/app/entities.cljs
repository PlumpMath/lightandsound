(ns lightandsound.entities
  (:require [clojure.set :as set]))

(defn get-types
  "gets all the types of the components"
  [components]
  (set (map :type components)))

(defn get-with-components
  [entity-map components]
  (->> (filter
        (fn [[k comps]]
          (set/subset? (get-types comps) components))
        entity-map)
       (into {})))

(defn change-entities
  "replaces the changed entities"
  [entity-map new-entities]
  (merge entity-map new-entities))