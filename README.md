
> To make a Dadaist poem:

> Take a newspaper.

> Take a pair of scissors.

> Choose an article as long as you are planning to make your poem.

> Cut out the article.

> Then cut out each of the words that make up this article and put them in a bag.

> Shake it gently.

> Then take out the scraps one after the other in the order in which they left the bag.

> Copy conscientiously.

> The poem will be like you.

> And here are you a writer, infinitely original and endowed with a sensibility that is charming though beyond the understanding of the vulgar.

- Tristan Tzara

# Tzara

A Clojure library with some utility functions for text-mining. Designed for:

- Quick and easy drop-in use in Clojure data science workflows; and

- Usage in data science workflows in other languages that can benefit from JVM speed and Clojure's easy paralleism capacity. (Also, the APIs of existing text-mining libraries in Python and R are way too complicated and make me sad.)

## New Plan/Vision

New vision for Tzara: just the transformations (most of which are already implemented)

Read docs as CSV or JSON. (Header row required for former.). Also pass in a configuration map that specifies:

1.  The field that has the docs (rest assumed to be extra columns in resulting data) 

2. Declarative transformations, as follows:

-- Cleaners: punctuation, numbers, case, stemmer, stopword removal, sparse term removal (with a proportion config).  All optional, can have 0 or as many as needed. Order counts, since what counts as sparse or stopword or whev is going to vary before or after stemming, removing punctuation, etc.

-- Tokenizers: word, word ngram (with n config), character ngram (with n config), sentence (only if punctuation is preserved, only if English), regex. At least one mandatory, additional ones will be added as extra columns. Order doesn't matter, each tokenizer starts with fresh copy of cleaned set. If none given, defaults to just word.

-- Matrixers: count or tfidf (others??).  One required, defaults to count if none given)

Then this can be a library. Run from commandline taking JSON for config map.  Or Cli can also create a http server to take JSON config map and data programmatically from external workflow. Basically like one of those python libraries that can be a commandline app too. 

With that plan, really all I need to do before first release is:

- [ ] wrap more stemmers

- [x] make sure the tests that I haven't looked at for months still pass.

- [ ] reorganize the code to make it easy to contribute and to have namespace hierarchies follow the cleaners/tokenizers/matrixers structure

- [ ] write an external api to take config map as above

- [ ] write a commandline entry point

- [ ] integrate a quick http server (maybe with liberator?  Or just ring + jetty? It's not like I need routing. Just to be able to take a put request... https://github.com/ring-clojure/ring/wiki/Getting-Started Aleph looks like it can just handle simple as well... https://github.com/ztellman/aleph)

- [ ] optimize a little (transducers, reducers, parallelism where possible)

- [ ] add some docs.




## License

Copyright © 2016 Paul Gowder

The MIT License (MIT)
Copyright (c) <2016> <Paul Gowder>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

Depends on a handful of non-MIT libraries, but all with permissive licenses (Eclipse, BSD, etc.)
