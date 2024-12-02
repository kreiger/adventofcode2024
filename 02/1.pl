#!/usr/bin/perl -w

use warnings;
use strict;
use feature 'say';
use List::Util qw( zip );


my $total = 0;

REPORT: while (<>) {
    my @levels = split /\s+/;
    my $trend = $levels[0] <=> $levels[1];
    my $prev = shift @levels;
    for (@levels) {
      next REPORT if $trend != ($prev <=> $_);
      next REPORT if abs($prev - $_) > 3;
      $prev = $_;
    }
    $total++;
}

say $total;
