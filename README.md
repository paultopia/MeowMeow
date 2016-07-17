# Tzara

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

A Clojure library with some utility functions for text-mining. Designed for:

- Quick and easy drop-in use in Clojure data science workflows; and

- Usage in data science workflows in other languages that can benefit from JVM speed and Clojure's easy paralleism capacity. For small datasets, the JVM startup time probably isn't worth it, but for large datasets Clojure's parallelism should help, especially with stemming. (Also, the APIs of existing text-mining libraries in Python and R are way too complicated and make me sad.)

Right now this is in early stage, and just has term-document matrix creation, basic text manipulation (removing punctuation, removing numbers, lowercasing, etc.), tf-idf scoring, and n-grams. The long-term goal is to replicate the functionality of packages like R's TM, but more usable.

Another planned future development is a standalone application, built on this library, that can just accept and transform data from the commandline, or through starting a local HTTP server. 

Contributions solicited, either for any of the todos below, anything else you want to add, or optimizations to anything that exists.

## Conceptual Framework and Manifesto

(n.b. none of this stuff is built yet, it's really the vision I have by the time of first release.  which should be soon, because I need this for another project.)

In order to get useful features out of text data, represented as a vector of documents, you need to generate tokens with **tokenizers**, which can be things like individual words, n-grams, character strings, regular expression matches, fuzzy regular expression matches, etc. Various tokenizers to create tokens have **options**, like "don't include punctuation" or "ignore case." Token sets can be subject to **transformations**, such as sparse terms removal, stemming (for word tokens), tf-idf scoring, etc. 

Let's call an internally consistent composition of tokenizers, options, and transformations a **feature representation**. To be maximally useful, a library for feature enginnering of documents should allow users to apply multiple inconsistent feature representations to a set of documents, such that, for example, columns 1-n of a feature matrix are word counts, while n+1-m are sentences or characters or n-grams. 

Users should also be able to easily update feature representations with the addition of new documents---each feature representation should contain enough information to permit, e.g., users to store a document set in an atom and then hang a watcher on it, such that the addition of new documents automatically updates the feature matrix (including updating rows for preexisting documents to the extent those rows depend on new information, such as potential new information about the overall frequency of terms).

That's the goal. Working on it.

## Usage

FIXME



### TODO

- [ ] Wrap major stemmers.

- [x] Modify main entry function to also take a vector of maps s.t. each map is document + keyword-denoted variables (like label + other features) (high priority)

- [ ] Support for sparse matrix.

- [ ] Sparse terms removal.

- [ ] Removal of short words by user-provided length. (Also, I suspect the current implementation with mapping over strings as characters will puke on any one-character documents.)

- [ ] split up some namespaces would be nice

- [x] basic n-grams.

- [ ] integrate n-grams into workflow

- [ ] big refactor and tidy.

- [ ] Stopwords removal.

- [ ] TF/IDF scores.

- [ ] Figure out how to leverage lazy data structures to handle large datasets.

- [ ] Tests (in process).

- [ ] Wrap sentiment analysis from existing NLP libraries (maybe to different library).

- [ ] I/O, create interfaces for common document storage methods, dump to CSV (maybe elsewhere).

- [ ] Wrap PDFBox and other readers for non-plaintext formats (maybe elsewhere).

- [ ] Create Python and R interfaces (possibly as separate CLI application or via http).

(notes to self: namespace organization by data level? tokenizers (including word split/1-gram and character-n-gram; make a sentence one?) as well as universal character transforms like tolower and remove punctuation operate on individual texts as single string; stemmers and word n-grams work on document as vector of tokens (and stemmers should be applied first); tf-idf (right?), sparse terms removal, tdm creation, etc., work on dataset as whole.  should also supply convenience functions to operate on dataset as a whole after applying lower-level transforms.)

(possible edge case note to self: where there's a chunk of punctuation surrounded by spaces, stripping punctuation will leave extra spaces, which will show up as blank strings after splitting.  After splitting, should run through something like `(remove clojure.string/blank?)` per discussion on http://www.markhneedham.com/blog/2013/09/22/clojure-stripping-all-the-whitespace/)

## License

Copyright © 2016 Paul Gowder

The MIT License (MIT)
Copyright (c) <2016> <Paul Gowder>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

Depends on a handful of non-MIT libraries, but all with permissive licenses (Eclipse, BSD, etc.)
