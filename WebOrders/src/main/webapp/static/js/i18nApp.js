
const backOpt = { 
      loadPath: 'bundle/messages?lang={{lng}}',
      addPath: 'bundle/messages/',
      requestOptions: (payload) => ({ method: 'GET' }),
      allowMultiLoading: false,
      crossDomain: true
    }

const detectOpt = {
  // order and from where user language should be detected
  order: ['querystring', 'cookie'],

  // keys or params to lookup language from
  lookupQuerystring: 'lang',
  lookupCookie: 'org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE',

  // cache user language on
  caches: ['cookie'],
  excludeCacheFor: ['cimode'], // languages to not persist (cookie)

  // optional expire and domain for set cookie
  cookieMinutes: 10,
  cookieOptions: { path: '/', sameSite: 'strict', Secure: 'true' }
}

const i18nOpt = {
    fallbackLng: 'en',
    debug: true,
    preload: ['en'],
    load: 'all',
    nonExplicitSupportedLngs: true,
    ns: [''],
    defaultNS: '',
    keySeparator: false,
    backend: backOpt,
    detection: detectOpt
}

const jquery18nOpt = {
  tName: 't', // --> appends $.t = i18next.t
  i18nName: 'i18n', // --> appends $.i18n = i18next
  handleName: 'localize', // --> appends $(selector).localize(opts);
  selectorAttr: 'data-i18n', // selector for translating elements
  targetAttr: 'i18n-target', // data-() attribute to grab target element to translate (if different than itself)
  optionsAttr: 'i18n-options', // data-() attribute that contains options, will load/set if useOptionsAttr = true
  useOptionsAttr: false, // see optionsAttr
  parseDefaultValueFromContent: true // parses default values from content ele.val or ele.text
}

i18next
  .use(i18nextHttpBackend)
  .use(i18nextBrowserLanguageDetector)
  .init(i18nOpt);
jqueryI18next.init(i18next, $, jquery18nOpt);


// just set some content and react to language changes
// could be optimized using vue-i18next, jquery-i18next, react-i18next, ...
function updateContent() {
  $('*[' + jquery18nOpt.selectorAttr + ']').localize()
}

i18next.on('languageChanged', () => {
  updateContent();
});
