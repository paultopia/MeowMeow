
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

[![Build Status](https://travis-ci.org/paultopia/tzara.svg?branch=master)](https://travis-ci.org/paultopia/tzara)

A Clojure library with some utility functions for text-mining. Designed for:

- Quick and easy drop-in use in Clojure data science workflows; and

- Usage in data science workflows in other languages that can benefit from JVM speed and Clojure's easy paralleism capacity. (Also, the APIs of existing text-mining libraries in Python and R are way too complicated and make me sad.)

## New Plan/Vision

New vision for Tzara: just the transformations (most of which are already implemented)

Read docs as CSV or JSON. (Header row required for former.). Also pass in a configuration map that specifies:

1.  The field that has the docs (rest assumed to be extra columns in resulting data, e.g., labels, other features) 

2. Declarative transformations, as follows:

-- Cleaners: punctuation, numbers, case, stopword removal. All optional, can have 0 or as many as needed. 

-- Tokenizers: word, word ngram (with n config), character ngram (with n config), regex. At least one mandatory, additional ones will be added as extra columns. Order doesn't matter, each tokenizer starts with fresh copy of cleaned set. If none given, defaults to just word.

-- Postprocessors: stemming, sparse token removal (with a proportion config). All optional, can have 0 or as many as needed.

-- Matrixers: count, tfidf (with several normalixation options), or binary-presence.  One required, defaults to count if none given)

**Cleaners** work on a sequence of strings (e.g. vector of documents) and return a sequence of modified strings. Available cleaners include: 

- Remove punctuation.

- Lowercase all characters.

- Remove numbers.

- Remove stopwords (english language only, taken from NLTK list).  (TO ADD: remove user-specified stopwords. Or is this already in there?)

**Tokenizers** work on a sequence of strings and return a sequence of sequences of strings (tokens). Available tokenizers include:

- Individual words (classic bag of words representation).

- Word n-gram for arbitrary n.

- Character n-gram for arbitrary n.

- Regular expression (finds matches, calls them tokens).

**Postprocessors** work on a sequence of sequences of strings (tokens) and return a sequence of sequences of strings. Available postprocessors include: 

- Stemmers (snowball (to be added: porter, others).

- Sparse token removal (with configurable proportion of sparsity).

**Matrixers** work on a sequence of sequences of strings and return a matrix where columns represent tokens, rows represent documents, and entries represent scores. Available matrixers include: 

- TF-IDF scores (with TF scores raw, normalized by document length, or normalized by number of unique tokens in document).

- Token counts.

- Binary presence or absence of token.

**I/O** handles conversion from json or csv, extraction of text portions and conversion into sequence of strings, and then recombination of text portions as TDM with remaining column and production either as vector of vectors or as CSV


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
