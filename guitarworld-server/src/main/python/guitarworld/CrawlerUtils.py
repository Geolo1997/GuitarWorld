import urllib.request


def get_html(url):
    return urllib.request.urlopen(url).read().decode('utf-8', 'ignore')
