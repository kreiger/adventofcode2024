#!/usr/bin/perl -w

use warnings;
use strict;
use feature 'say';
use List::Util qw( zip );


my (@l, @r);

while (<>) {
    my ($l, $r) = /(\d+)\s+(\d+)/;
    push @l, $l;
    push @r, $r;
}

@l = sort { $a <=> $b } @l;
@r = sort { $a <=> $b } @r;

my $total = 0;
for (zip \@l, \@r) {
    my ($l, $r) = ($_->[0], $_->[1]);

    my $distance = abs($l - $r);

    $total += $distance;

}

say $total;