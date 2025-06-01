INSERT INTO provider (name, endpoint)
  VALUES ('RestCountries', 'https://restcountries.com/v3.1/')
ON CONFLICT (name) DO NOTHING;
