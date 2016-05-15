# MeowMeow

A Clojure library with some utility functions for text-mining. Designed for:

- Quick and easy drop-in use in Clojure data science workflows; and

- Usage in data science workflows in other languages that can benefit from JVM speed and Clojure's easy paralleism capacity. For small datasets, the JVM startup time probably isn't worth it, but for large datasets Clojure's parallelism should help, especially with stemming. (Also, the APIs of existing text-mining libraries in Python and R are way too complicated and make me sad.)

Right now this is in early stage, and just has term-document matrix creation, basic text manipulation (removing punctuation, removing numbers, lowercasing, etc.), tf-idf scoring, and n-grams. The long-term goal is to replicate

Contributions solicited, either for any of the todos below, anything else you want to add, or optimizations to anything that exists.


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
