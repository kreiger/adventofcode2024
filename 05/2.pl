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
    my $rule_broken;
    for (@pages) {
        my $page = $_;
        for (@seen) {
            my $seen = $_;
            if ($rules{$page}{$seen}) { 
                $rule_broken = 1;
                $_ = $page;
                $page = $seen;
            }
        }

        push @seen, $page;
    }
    next UPDATE unless $rule_broken;
    say join ',', @pages;
    say join ',', @seen;

    my $middle_index = @seen >> 1;
    $total += $seen[$middle_index];
}

say $total;
