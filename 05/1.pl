#!/usr/bin/perl -w

use warnings;
use strict;
use feature 'say';

my %rules = ();

while (<>) {
    last unless /(\d+)\|(\d+)/;
    $rules{$1}{$2}++;
}

my $total = 0;
UPDATE: while (<>) {
    chomp;
    my @pages = split ',';
    my @seen = ();
    for my $page (@pages) {
        my $rule_broken = grep { $rules{$page}{$_} } @seen;
        next UPDATE if $rule_broken;
        push @seen, $page;
    }
    my $middle_index = @pages >> 1;
    $total += $pages[$middle_index];
}

say $total;
