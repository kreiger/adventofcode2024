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
    my $permutations_count = 3 ** @values;
    for (my $permutation = 0; $permutation < $permutations_count; $permutation++) {
        my $p = $permutation;
        my $acc = $initial;
        for my $v (@values) {
            my $op = $p % 3;
            $p /= 3;
            if ($op == 0) {
                $acc += $v;
            } elsif ($op == 1) {
                $acc *= $v;
            } elsif ($op == 2) {
                $acc .= $v;
            }
        }
        if ($acc == $tv) {
            $total += $tv;
            last;
        }
    }
}

say $total;