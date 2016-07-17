(ns tzara.utilities
  "helper functions that show up in multiple namespaces. Not meant for public use."
  )

(defn- numdocs-t-helper
  "given a column represented as a bunch of individual entries in that column, 
  (NOT as a vector) returns the number of entries that are above zero"
  [& args]
  (count (filter #(< 0 %) args)))

(defn ccp
  "this is the count of documents in which a given token appears"
  [tdm]
  (apply map numdocs-t-helper tdm))
  
  (def count-column-presences (memoize ccp))
  
  (defn filter-by-other 
    "filters target based on applying pred to test.  probably will blow up 
    with infinite sequences, and god only knows what will 
    happen with lazy ones.  Assumes target and test are vectors of equal length, 
    pred is a single-arity function"
    [target test pred]
    (let [pairs (map vector target test)]
      (mapv first (filter #(pred (second %)) pairs))))



