#!/usr/bin/perl

use warnings;
use strict;
use Carp;
use Data::Dumper;

use feature 'say';
#use feature 'bitwise';

my %nodes = ();

while (<>) {
     if (/^(\w+): (\d+)/) {
        my ($node, $val) = ($1, $2);
        $nodes{$node} = sub { 0+$val };
        next;
     }

     if (/^(\w+) (\w+) (\w+) -> (\w+)/) {
         my ($n0, $op, $n1, $node) = ($1, $2, $3, $4);
         if ($op eq 'XOR') {
             $nodes{$node} = sub { ($nodes{$n0}->()) ^ ($nodes{$n1}->()) };
         } elsif ($op eq 'AND') {
             $nodes{$node} = sub { $nodes{$n0}->() & $nodes{$n1}->() };
         } elsif ($op eq 'OR') {
             $nodes{$node} = sub { $nodes{$n0}->() | $nodes{$n1}->() };
         } else {
             die;
         }
     }

}

my $result = 0;
for my $node (reverse sort grep { /^z/ } keys %nodes) {
    my ($bit) = $node =~ /z(\d+)/;
    my $val = (1 << (0+$bit));
    $result |= $val if $nodes{$node}->();
}
say $result;
