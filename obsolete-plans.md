# OBSOLETE PLANS

## OBSOLETE Conceptual Framework and Manifesto

(n.b. none of this stuff is built yet, it's really the vision I have by the time of first release.  which should be soon, because I need this for another project.)

In order to get useful features out of text data, represented as a vector of documents, you need to generate tokens with **tokenizers**, which can be things like individual words, n-grams, character strings, regular expression matches, fuzzy regular expression matches, etc. Various tokenizers to create tokens have **options**, like "don't include punctuation" or "ignore case." Token sets can be subject to **transformations**, such as sparse terms removal, stemming (for word tokens), tf-idf scoring, etc. 

Let's call an internally consistent composition of tokenizers, options, and transformations a **feature representation**. To be maximally useful, a library for feature enginnering of documents should allow users to apply multiple inconsistent feature representations to a set of documents, such that, for example, columns 1-n of a feature matrix are word counts, while n+1-m are sentences or characters or n-grams. 

Users should also be able to easily update feature representations with the addition of new documents---each feature representation should contain enough information to permit, e.g., users to store a document set in an atom and then hang a watcher on it, such that the addition of new documents automatically updates the feature matrix (including updating rows for preexisting documents to the extent those rows depend on new information, such as potential new information about the overall frequency of terms).

That's the goal. Working on it.

## Usage

FIXME



### OBSOLETE TODO

- [ ] Wrap major stemmers.

- [x] Modify main entry function to also take a vector of maps s.t. each map is document + keyword-denoted variables (like label + other features) (high priority)

- [ ] Support for sparse matrix.

- [x] Sparse terms removal.

- [ ] Removal of short words by user-provided length. (Also, I suspect the current implementation with mapping over strings as characters will puke on any one-character documents.)

- [x] split up some namespaces would be nice

- [x] basic n-grams.

- [ ] integrate n-grams into workflow

- [ ] big refactor and tidy.

- [x] Stopwords removal.

- [x] TF/IDF scores.

- [ ] Figure out how to leverage lazy data structures to handle large datasets.

- [ ] Tests (in process).

- [ ] Wrap sentiment analysis from existing NLP libraries (maybe to different library).

- [ ] I/O, create interfaces for common document storage methods, dump to CSV (maybe elsewhere).

- [ ] Wrap PDFBox and other readers for non-plaintext formats (maybe elsewhere).

- [ ] Create Python and R interfaces (possibly as separate CLI application or via http).

- [ ] Implement or wrap LDA

- [ ] Wrap important bits of a java text mining library (?) http://opennlp.apache.org ? https://stanfordnlp.github.io/CoreNLP/ ? Looks like the stanford one has existing partial wrappers. https://github.com/damienstanton/stanford-corenlp https://github.com/ngrunwald/stanford-nlp-tools Ditto apache https://github.com/dakrone/clojure-opennlp

(notes to self: namespace organization by data level? tokenizers (including word split/1-gram and character-n-gram; make a sentence one?) as well as universal character transforms like tolower and remove punctuation operate on individual texts as single string; stemmers and word n-grams work on document as vector of tokens (and stemmers should be applied first); tf-idf (right?), sparse terms removal, tdm creation, etc., work on dataset as whole.  should also supply convenience functions to operate on dataset as a whole after applying lower-level transforms.)

(possible edge case note to self: where there's a chunk of punctuation surrounded by spaces, stripping punctuation will leave extra spaces, which will show up as blank strings after splitting.  After splitting, should run through something like `(remove clojure.string/blank?)` per discussion on http://www.markhneedham.com/blog/2013/09/22/clojure-stripping-all-the-whitespace/)
