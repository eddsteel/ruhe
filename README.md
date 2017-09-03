# ruhe

[![Build Status](https://travis-ci.org/eddsteel/ruhe.svg?branch=master)](https://travis-ci.org/eddsteel/ruhe)


## ~~Slice 0~~
scala.js + canvas + randomly spaced lines

## ~~Slice 1~~
lines moving at random speeds
base everything off a single seed value -- to allow reproduction
random number of trees
Tree has starting position and speed
position is derived from speed and time -- allows time to move in any direction
make all the tree state 0..1

## ~~Slice 2~~
Random speeds used to determine saturation/layer index (overlap should work)
can probably just order by speed and draw in correct order.
Trees with variable thickness

## Slice 3
Deployable in a docker container (i.e. a server side component that serves html/js)
Built in travis

## Slice 4

Expose config parameters in a UI
