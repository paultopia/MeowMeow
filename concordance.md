# Concordance (OBSOLETE)

New concept: the concordance. 

Example: statutory citation changes over time.

Represent as a three-dimensional table (matrix): 

- dim 1 = function expressing conditions for application (i.e. if 1995 < yr 2000, apply table A, otherwise apply table b)

- dims 2, 3 = 2xn tables where first column is original identifier and second column is new identifier (i.e. conversions), when table is called by function in first dimension. 

should be fairly easy to implement. this probably calls for a deftype. And then some kind of serialized format? 

Underlying implementation is as a set of function/matrix mappings, i.e., 

```Clojure
#{{func f1 conversions [[foo bar] [bar baz]]} {func f2 conversions [[bar foo] [foo baz]]}}
```

Or is that stupid? If conversions are just mappings then why not represent them as maps and get O(1) access? I.e.: 

```Clojure
#{{func f1 conversions {foo bar bar baz}} {etc "..."}}
```

Also will need to have: 

- implement IFn and have a concordance callable on a dataset?  That's probably really stupid; ought to just have an `apply-concordance` function

- some kind of rule for the order in which conversions in a given concordance are to be applied (ordinarily should be simultaneous, and that's a sensible default, but sometimes may be ordered in order to override previous conversions?  Perhaps `apply-concordance` should take an options map and, if non-simultaneous, a function describing the ordering.)

- some kind of specification of where in a document processing pipeline this makes sense?  One possibility is on tokens, but there might be conversions that don't map onto tokens. So probably makes more sense to apply at the raw text stage. 

- correction to above: actually, that seems a little iffy. Applying at text stage means simultaneous search and replace on chunks of text that, in the worst case scenario, could be overlapping; I don't know that I want to mess with that (although this listserv thread on multiple replacements seems useful: https://groups.google.com/forum/#!topic/clojure/SRGGYeFhML8 ); probably makes more sense to just handle at the token level, then it's as simple as a map lookup over every token once. 

though if I can assume no overlaps then the clever idea in one of the messages in that thread of using clojure.string/replace with regex pipe-ors and a map called as a function even works with circular replacements.  Tested out in planck: 

```Clojure
(require '[clojure.string :as s])
(s/replace "a b c" #"a|b|c" {"a" "b" "b" "c" "c" "a"})
;; => "b c a"
```


- serialization? just use transit --> EDN, and represent functions as lists? Maybe something from here?  https://github.com/technomancy/serializable-fn -- wrap those suckers in `with-out-str` use a macro on concordance creation to grab all the string representations and hold onto them in a serializable form?  

- but really, what I care about is being able to read a concordance in and apply it rather than being able to write it out somewhere. In all honesty. If you've got a bunch of CSVs, each with a bunch of conversions, there ought to be some kind of constructor that takes all those CSVs and a function for each and returns a concordance. 
