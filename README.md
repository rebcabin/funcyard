# funcyard

An ever-growing scrap-heap of small, useful functions, the needs for which
popped up over and over in applications. For example, `(tally some-sequence)`
produces a map whose keys are the unique elements of `some-sequence` and whose
values are the numbers of times each key appears in `some-sequence`; `(tally ['a
'c 'b 'a 'c])` produces `{'a 2, 'b 1, 'c 2}` For another example, `(pairwise
binary-fn some-sequence)` applies the binary function to pairs of inputs from
`some-sequence` shifted left by one and `some-sequence`; `(pairwise - [4, 9, 16,
25])` produces `[(- 9 4) (- 9 16) (- 16 25)]` or `[5 7 9]`.

How many times have you needed little things like this, implemented them again
ad-hoc, and filled up your application namespaces with technical debt?

## Synopsis

    (tally               collection                 )
    (pairwise            binary-function collection )
    (transpose           matrix                     )

### Combinatorics

    (combinations        collection      m-at-a-time)
    (permutations        collection                 )
    (string-permutations string                     )

### Mapping

    (map-down-one        function        collections)
    (riffle              collection1     collection2 [optional-combiner])
    (rifflecat           collection1     collection2 [optional-combiner])

### Cardinalities

    (has-duplicates      collection                 )

## Goals

1. Elegant, easy to read.
2. A nice place to put things you use over and over again, instead of
   re-implementing them and building up ad-hoc technical debt. 

## Non-Goals

1. Performance is a non-goal in this version.
2. Type safety --- call these with the wrong types of input and anything can
   happen
3. Test coverage --- Tests are minimal and heavily biased to the happy path. 
4. Organization is a non-goal. This is a scrap-heap or junkyard full of stuff
   you might need to use, but not sure when or how.

## TODO: Future

1. Transducer-friendly
2. Leverage clojure.spec
3. Dotted namespaces; separate group-id from artifact-id
4. Professional level of robustness, organization, and performance

## License

Copyright © 2017 Reb Cabin

Distributed under The MIT License (MIT)

https://tldrlegal.com/license/mit-license
