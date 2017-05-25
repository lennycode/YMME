#!/usr/bin/perl

use 5.010;#
use strict;
use warnings;

use LWP::Simple;
use LWP::UserAgent;
use HTTP::Cookies;
use HTTP::Request::Common qw(POST);
use HTTP::Request::Common qw(GET);
use Data::Dumper;

sub models($) {
    my $string = shift;
    my@ models = ($string = ~m / < option value = "[\w\s\/\-]*?" > ([\w\ s\ / \-] * ? ) < \/option>/g);
    return@ models;
}

sub engines($) {
    my $string = shift;
    my@ engines = ($string = ~m / < option value = .* ?> (\d.* ? ) < \/option>/g);
    return@ engines;
}

sub trim($) {#
    Perl does not have a trim statment, so here goes!
        my $string = shift;
    $string = ~s / ^ \s + //;
        $string = ~s / \s + $ //;
    return $string;
}

my $year = "";
if ($ARGV[0] eq "") {
    print "Usage: adv2.pl numericYearToPull";
    exit;
} else {
    $year = $ARGV[0];
}

$ENV {
    'PERL_LWP_SSL_VERIFY_HOSTNAME'
} = 0;
my $ua = LWP::UserAgent - > new;


$ua - > cookie_jar(
    HTTP::Cookies - > new(
        file => 'mycookies.txt',
        autosave => 1
    )
);


my@ ff_like_headers = (
    'User-Agent' => 'Mozilla/5.0 (Windows NT 6.1; rv:2.0) Gecko/20100101 Firefox/4.0',
    'Accept-Language' => 'en-us,en;q=0.5',
    'Accept-Charset' => 'ISO-8859-1,utf-8;q=0.7,*;q=0.7',
    'Accept-Encoding' => 'gzip, deflate',
    'Accept' => 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8'
);

#
For fiddler# $ua - > proxy(['http', 'ftp', 'https'], 'http://127.0.0.1:8888/');

my $res = $ua - > get('http://shop.advanceautoparts.com/home', @ff_like_headers);

print $res - > decoded_content;




my $can_accept = HTTP::Message::decodable;

#@
years = (1980..2011);



sleep(1);
my@ ff_like_headers_HR = (
    'User-Agent' => 'Mozilla/5.0 (Windows NT 6.1; rv:2.0) Gecko/20100101 Firefox/4.0',
    'Accept-Language' => 'en-us,en;q=0.5',
    'Accept-Charset' => 'ISO-8859-1,utf-8;q=0.7,*;q=0.7',
    'Accept-Encoding' => 'gzip, deflate',
    'Accept' => '*/*',
    'X-Requested-With' => 'XMLHttpRequest',
    'Referer' => 'http://shop.advanceautoparts.com/home',
    'Keep-Alive' => '115',
    'Proxy-Connection' => 'keep-alive'
);#
sleep(1);

open(MYFILE, ">>$year.text");
say "http://shop.advanceautoparts.com/web/FetchVehicleDetailsView?YMMEAction=wantMake&vehicleType=3&year=$year&storeId=10151&catalogId=10051&langId=-1";
$res = $ua - > get("https://shop.advanceautoparts.com/web/FetchVehicleDetailsView?YMMEAction=wantMake&vehicleType=3&year=$year&storeId=10151&catalogId=10051&langId=-1", @ff_like_headers_HR);
sleep(1);

$_ = $res - > decoded_content;

# //say $_;
my $i = 0;
my % ehash = ();
my $enum = 0;
my $e = 0;
while (/<option value="[\w\s\/]*?">([\w\s\/]*?)<\/option>/g) {
    $i++;#
    if ($i == 2) {
        last;
    }

    say $1;
    $make = $1;#
    https: //shop.advanceautoparts.com/web/FetchVehicleDetailsView?YMMEAction=wantModel&vehicleType=3&year=2014&make=Buick&storeId=10151&catalogId=10051&langId=-1
        $req_mod_url = "https://shop.advanceautoparts.com/web/FetchVehicleDetailsView?YMMEAction=wantModel&vehicleType=3&year=$year&make=$make&storeId=10151&catalogId=10051&langId=-1";#
    say $req_mod_url;
    $res = $ua - > get($req_mod_url, @ff_like_headers_HR);

    @
    mod_arr = models($res - > decoded_content);
    say map {
        $_.
        "\n"
    }@
    mod_arr;

    for $mod(@mod_arr) {
        $mod = $mod;
        $req_eng_url = "https://shop.advanceautoparts.com/web/FetchVehicleDetailsView?YMMEAction=wantEngine&vehicleType=3&year=$year&make=$make&model=$mod&storeId=10151&catalogId=10051&langId=-1";#
        say $req_eng_url;
        $res = $ua - > get($req_eng_url, @ff_like_headers_HR);
        my $range = 3;
        my $minimum = 3;

        my $random_number = int(rand($range)) + $minimum;
        sleep($random_number);

        @
        eng_arr = engines($res - > decoded_content);
        say $mod;
        print MYFILE map {
            "$year, $make, $mod,".$_.
            "\n";
        }@
        eng_arr;
        say map {
            "$year, $make, $mod,".$_.
            "\n";
        }@
        eng_arr;
        for $eng(@eng_arr) {
            $ehash {
                $eng
            } = $e++;
        }

    }

}
say "---";
open(MYEFILE, ">>$year".
    "engine.text");
while (($key, $value) = each( % ehash)) {
    say MYEFILE "$key,$value";
}


close(MYFILE);
close(MYEFILE);