from src.main.python.per.geolo.guitarworld.GuitarChinaCrawler \
    import get_html, GUITAR_CHINE_URL, get_banner_item, print_informations

from src.main.python.per.geolo.guitarworld.MySQL import *

if __name__ == '__main__':
    html = get_html(GUITAR_CHINE_URL)
    # print(html)
    infos = get_banner_item()
    clear_informations()
    insert_informations(infos)

