#!/usr/bin/perl -w

use warnings;
use strict;
use feature 'say';
use List::Util qw( zip );


my $total = 0;

sub bad_report {
    my @levels = @_;
    my $trend = $levels[0] <=> $levels[1];
    my $prev = shift @levels;
    for (@levels) {
      return 1 if $trend != ($prev <=> $_);
      return 1 if abs($prev - $_) > 3;
      $prev = $_;
    }
    return 0;
}

REPORT: while (<>) {
    my @levels = split /\s+/;
    for my $i (0..$#levels) {
        my @l = (@levels[0..($i-1)],@levels[($i+1)..$#levels]);
        if (!bad_report(@l)) {
            $total++;
            next REPORT;
        }
    }
}

say $total;
