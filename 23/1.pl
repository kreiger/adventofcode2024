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
my %sets = ();

my @computers = keys %conn;

for my $comp0 (sort keys %conn) {
    my @conn = sort keys %{$conn{$comp0}};
    while (my $comp1 = shift @conn) {
       for my $comp2 (grep { $conn{$comp1}{$_} } @conn) {
           my @key = ($comp0, $comp1, $comp2);
           my $key = join ',', sort @key;
           $sets{$key}++ if grep { /^t/ } @key;
       }
    }
}

say Dumper(\%sets);
say scalar keys %sets;
