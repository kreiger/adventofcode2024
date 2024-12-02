#!/usr/bin/perl -w

use warnings;
use strict;
use feature 'say';
use List::Util qw( zip );


my (%l, %r);

while (<>) {
    my ($l, $r) = /(\d+)\s+(\d+)/;
    $l{$l}++;
    $r{$r} += $r;
}

my $total = 0;
for (sort keys %l) {
    $total += $r{$_} || 0;

}

say $total;