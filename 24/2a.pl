#!/usr/bin/perl -w

use warnings;
use strict;

use feature 'say';

say "digraph adders {";
say "  node [ordering=out]";

while (<>) {
    next unless /^(\w+) (\w+) (\w+) -> (\w+)/;
    my ($l, $op, $r, $node) = ($1, $2, $3, $4);
    say "  $node$op [label=$op]";
    say "  $node [label=$node]";
    say "  $l -> $node$op";
    say "  $r -> $node$op";
    say "  $node$op -> $node";
}

for (my $i = 0; $i < 45; $i++) {
    say sprintf "  subgraph cluster_adder%02d { x%02d; y%02d; z%02d }", $i, $i, $i, $i;
}

say "}";