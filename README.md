# funcyard

An ever-growing scrap-heap of small, ridiculously useful functions, the needs
for which popped up over and over in applications. For example, `(tally
some-sequence)` produces a map whose keys are the unique elements of
`some-sequence` and whose values are the numbers of times each key appears in
`some-sequence`; `(tally ['a 'c 'b 'a 'c])` produces `{1 2, 2 1, 3 2}` For
another example, `(pairwise binary-fn some-sequence)` applies the binary
function to pairs of inputs from `some-sequence` shifted left by one and
`some-sequence`; `(pairwise - [4, 9, 16, 25])` produces 
`[(- 9 4) (- 9 16) (- 16 25)]` or `[5 7 9]`.

How many times have you needed little things like this, implemented them again
ad-hoc, and filled up your application namespaces with technical debt?

## Usage

FIXME

## License

Copyright © 2016 Reb Cabin

Distributed under The MIT License (MIT)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

https://tldrlegal.com/license/mit-license
