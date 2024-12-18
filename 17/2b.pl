#!/usr/bin/perl

use warnings;
use strict;
use feature 'say';
use List::Util 'all';

local $/ = '';

my $registers = <>;
my ($startA,$startB,$startC) = $registers =~ /(\d+)/g;


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

my $IP = 0;

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

my @output = @program;
#



for (my $test = 245654000; ; $test++) {
    ($IP,$A,$B,$C) = (0,$test,$startB,$startC);
    @output = ();
    say $test if 0 == $test % 1000;
    for ( ;$IP < $#program; $IP+=2) {
        my $opcode = $program[$IP];
        my $op = $program[$IP+1];
        my $mnemonic = $instructions[$opcode];
        my $instruction = $instructions{$mnemonic};
        my $combo = $combo[$op]->();
        my $operand = {lit => $op, combo => $combo};
        #say "Before IP:$IP A:$A B:$B C:$C $opcode/$mnemonic $op/$combo";
        last unless defined $instruction->($operand);
        #say "After  IP:$IP A:$A B:$B C:$C $opcode/$mnemonic $op/$combo";
#        say join ',', @output;
        #<>;
    }
    next unless scalar(@output) == scalar(@program);
    say $test and last if all { $output[$_] == $program[$_] } 0 .. $#program;
}

sub adv {
    $A = $A*(2**shift->{combo});
    return 1;
}

sub bxl {
    $B = $B ^ shift->{lit};
    return 1;
}

sub bst {
    $B = shift->{combo} & 7;
    return 1;
}

sub jnz {
    return 1 unless $A;
    $IP = shift->{lit}-2;
    return 1;
}

sub bxc {
    $B = $B ^ $C;
    return 1;
}

sub out {
    my $val = pop @output;
    return undef unless $program[scalar @output] == $val;
    push @output, $val;
    return 1;
}

sub bdv {
    $B = int($A/2**shift->{combo});
    return 1;
}

sub cdv {
    $C = int($A/2**shift->{combo});
    return 1;
}