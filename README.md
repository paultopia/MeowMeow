(work in progress, it'll meet the description below when I finally stick it up on clojars)

# Blabber

A Clojure library with some utility functions for text-mining. Designed for:

- Quick and easy drop-in use in Clojure data science workflows; and 

- Usage in data science workflows in other languages that can benefit from JVM speed and Clojure's easy paralleism capacity. For small datasets, the JVM startup time probably isn't worth it, but for large datasets Clojure's parallelism should help, especially with stemming. (Also, the APIs of existing text-mining libraries in Python and R are way too complicated and make me sad.)

Right now this is in pre-alpha, and just has term-document matrix creation, basic text manipulation (removing punctuation, removing numbers, lowercasing, etc.), and soon wrappers for some stemmers. Which is enough if you're just doing a simple bag-of-words.

Contributions solicited, either for any of the todos below, anything else you want to add, or optimizations to anything that exists.

## Usage

FIXME



### TODO

[ ] Wrap major stemmers (in process).

[ ] Support for sparse matrix.

[ ] Sparse terms removal.

[ ] N-grams.

[ ] Stopwords removal.

[ ] TF/IDF scores.

[ ] Figure out how to leverage lazy data structures to handle large datasets.

[ ] Tests.

[ ] Wrap sentiment analysis from existing NLP libraries.

[ ] I/O, create interfaces for common document storage methods, dump to CSV.

[ ] Wrap PDFBox and other readers for non-plaintext formats.

[ ] Create Python and R interfaces (possibly as separate CLI application or via http).

## License

Copyright © 2016 Paul Gowder

The MIT License (MIT)
Copyright (c) <year> <copyright holders>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

Depends on a handful of non-MIT libraries, but all with permissive licenses (Eclipse, BSD, etc.)
