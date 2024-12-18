#!/usr/bin/perl

use warnings;
use strict;
use feature 'say';
use List::Util 'all';

local $/ = '';

my $registers = <>;
my ($A,$B,$C) = $registers =~ /(\d+)/g;

my $program = <>;
my @program = $program =~ /(\d+)/g;

$/ = "\n";
#say $A;
#say $B;
#say $C;

my @combo = (
    sub { 0 },
    sub { 1 },
    sub { 2 },
    sub { 3 },
    sub { $A },
    sub { $B },
    sub { $C },
    sub { ... },
);

my @instructions = qw(
    adv
    bxl
    bst
    jnz
    bxc
    out
    bdv
    cdv
);

my %instructions = (
    'adv' => \&adv,
    'bxl' => \&bxl,
    'bst' => \&bst,
    'jnz' => \&jnz,
    'bxc' => \&bxc,
    'out' => \&out,
    'bdv' => \&bdv,
    'cdv' => \&cdv,
);

my @output = ();

my $IP = 0;
my @open = (3);
while (@output <= @program) {
    my $test = shift @open;
    ($A,$B,$C) = ($test,0,0);
    @output = ();
    for ($IP = 0 ;$IP < $#program; $IP+=2) {
        my $opcode = $program[$IP];
        my $op = $program[$IP+1];
        my $mnemonic = $instructions[$opcode];
        my $instruction = $instructions{$mnemonic};
        my $combo = $combo[$op]->();
        my $operand = {lit => $op, combo => $combo};
        #say "Before IP:$IP A:$A B:$B C:$C $opcode/$mnemonic $op/$combo";
        $instruction->($operand);
        #say "After  IP:$IP A:$A B:$B C:$C $opcode/$mnemonic $op/$combo";
    }
    next unless all { $program[-1-$_] == $output[-1-$_] } 0..$#output;
    say sprintf('%48b %16o %15d %32s',$test, $test, $test, join(',', @output));
    push @open, ($test << 3 | $_) for 0..7;
}



sub adv {
    $A = int($A/(2**shift->{combo}));
}

sub bxl {
    $B = $B ^ shift->{lit};
}

sub bst {
    $B = shift->{combo} & 7;
}

sub jnz {
    return unless $A;
    $IP = shift->{lit}-2;
}

sub bxc {
    $B = $B ^ $C;
}

sub out {
    push @output, shift->{combo} & 7;
}

sub bdv {
    $B = int($A/2**shift->{combo});
}

sub cdv {
    $C = int($A/2**shift->{combo});
}
