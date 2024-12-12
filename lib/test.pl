#!/usr/bin/perl

use warnings;
use strict;
use feature 'say';

use Test::Simple;
use lib '.';
use Vector2D;


ok( V(3,5) == V(2,3) + V(1,2) );
ok( V(1,1) == V(2,3) - V(1,2) );
ok( V(3,5) == [3,5] );
ok( [3,5] == V(3,5) );
ok( [-3,-5] == -V(3,5));