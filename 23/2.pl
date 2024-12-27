#!/usr/bin/perl

use warnings;
use strict;

use feature 'say';
use Data::Dumper;
use List::Util 'first';

my %conn = ();
while (<>) {
    my ($a, $b) = /^(\w+)-(\w+)/;

    $conn{$a}{$b} = 1;
    $conn{$b}{$a} = 1;
}

my @computers = sort keys %conn;

my @largest = ();
for my $comp0 (@computers) {
    my @clique = ( $comp0 );
    for my $comp1 (@computers) {
        next unless @clique == scalar grep { $conn{$comp1}{$_} } @clique;
        push @clique, $comp1;
    }
    @largest = @clique if @clique > @largest;
}

say join ',', sort @largest;
