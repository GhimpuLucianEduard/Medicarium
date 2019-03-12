# Medicarium

### Prof Coord: Dr. CZIBULA Istvan, Profesor Universitar

### tema aleasa: Aplicatii mobile - securitatea datelor si a aplicatiei

#### referinte: 

1. Privacy and data protection in mobile applications (ENISA 29.01.2019) 
https://www.enisa.europa.eu/publications/privacy-and-data-protection-in-mobile-applications

2. Smartphone Secure Development Guidelines (ENISA 27.02.2017) 
https://www.enisa.europa.eu/publications/smartphone-secure-development-guidelines-2016

3. OWASP Mobile Application Security Verification Standard  
https://github.com/OWASP/owasp-masvs

4. Android Documentation 
https://developer.android.com/topic/security/best-practices


# Use Cases

1. Open app
2. Splash Screen
3. [Login](##Login) 


## Login

1. First time login => [Create Account](##Create\ Account) else use pin or fingerprint
2. Main Page -> [Tabbed Page](##Tabbed\ Page)

## Create Account

1. Complete form
2. Receive email verification
3. Back to Login (A)

## Tabbed Page

1. [Generic Info](##Generic\ Info\ \(Tab\ 1)
2. [Medical History](##Medical\ History)

## Generic Info

A **grid** with big buttons which will push a new page with relevant information about the user. The buttons will display the most relevant information on that category.

Inside the new pushed page the user can edit the information after clicking on a icon in the nav bar. To confirm the edit, he will click again on the same icon which is changed to a check mark.

A list for the information:

1. Generic info 
   * Name, Surname
   * Age (birthday)
   * Height, Width 
   * Blood type

2. Active Drugs
3. Allergies 
4. ???

 ## Medical History

 Could be **grid**, still not sure about it. Could have the same style. Again buttons will redirect to
