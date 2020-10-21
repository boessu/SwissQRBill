# Swiss QRbill validate

Several validations useful for Swiss QR Bill. This implementation has two targets:
- Should be useful for quality checks of own implementations.
- Should be readable and understandable code for learning.

Beside of that it also includes general useful source code examples for the following checks in the package ch.braincell.pay.val. It seems to be rather hard to get these examples in the Net... so I separated them in these classes. Hopefully this is a useful place for people who'll search for these algorithms.

The class AccountVal includes the following:
- validating an IBAN formally (according to ISO 13616-1:2007, part 1), including the check of the checksum.
- format an IBAN to a String for the official, visual representation of an IBAN.
- validating a Swiss PostFinance account to be formally valid (checksum calculation)
- format a Swiss PostFinance account number to the official visual form with '-'.
- format a Swiss PostFinance account number to the official digital 9 digits form (e.g. for the coding lines on the payment slips).

The Class ReferenceVal includes the following:
- Check Creditor References according to ISO11649:2009 (SCOR, SEPA)
- Check QRR References (basically the same like ESR/ISR references)

The class Checksum includes the following:
- Calculate Modulo97 Checksums (used in IBAN, SCOR, ISO11649:2009, SEPA Creditor References)
- Calculate Modulo 10 Recursive (used in ISR/ESR/QRR references and Swiss PostFinance account numbers).

Although this library doesn't create any QR code. There are already really good open source implementations available for that.
So this library is for the curious programmers who search some solutions and know-how for their payments related code.

## Some things to keep an eye on in the the wild life of QR-bill payment slips
- **Keep the Error Correction Level to "M".** Yes, I know: most banks do accept Error Correction Level "H" too... as long as they can scan it with their scanner resolution. The minimal scanning and archiving level requirements of payment slips is 200 dpi and B/W. If you push the Error Correction Level to "H", you'll run into payment slips which won't be recognized by paper scanning processes if there is a bit more data in it (happens as soon as Ultimate Creditor will be enabled). And, just as a note, printers and their drivers could get into their bleeding edges too with 997 characters, the specified physical size of the QR code on a OR bill and Error Correction Level "H". So keep it to "M" and you (and your customers) don't get into troubles.
- **Use CR/LF or LF according to the implementation guide.** CR (only) payment data can run into troubles. Not all banks can accept that on all of their channels. Even if they do accept it on one channel (e.g. e-banking), you can't be sure they'll accept it on all other channels too. I know, the FAQ on https://www.paymentstandards.ch/ tells you a different story than the Implementation Guide. But in case of doubt the Implementation Guide overrules the FAQ (trust me).
- **Don't crop the borders of the QR code while printing.** If you loose parts of the three Position Markers, it won't be recognized by most scanners (e.g. https://zxing.org/). While the rest has a good protection against such problems, the Position Markers identify the QR code borders itself and are very important. That's also the reason why they've an eye-catching bigger size compared to the Alignment Markers and Data Points.
- **use UTF-8 for the data and use only allowed characters according to the Implementation Guildeline.** Very important. Banks *do* reject payments if there are not allowed characters in it due to compliance reasons.

Don't kill the messenger, please.