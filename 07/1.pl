#!/usr/bin/perl -w

use warnings;
use strict;
use feature 'say';

my $total = 0;
while (<>) {
    chomp;
    my ($tv, $equation) = split ': ';
    my @values = split ' ', $equation;
    my $initial = shift @values;
    my $permutations_count = 1 << @values;
    for (my $permutation = 0; $permutation < $permutations_count; $permutation++) {
        my $p = $permutation;
        my $acc = $initial;
        for my $v (@values) {
            if ($p & 1) {
                $acc *= $v;
            } else {
                $acc += $v;
            }
            $p >>= 1;
        }
        if ($acc == $tv) {
            $total += $tv;
            last;
        }
    }
}

say $total;