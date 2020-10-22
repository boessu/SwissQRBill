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
(Known problems when producing swiss qr bill's)
- **Keep the Error Correction Level to "M".** Yes, I know: most banks do accept Error Correction Level "H" too... as long as they can scan it with their scanner resolution. The minimal scanning and archiving level requirements of payment slips is 200 dpi and B/W. If you push the Error Correction Level to "H", you'll run into payment slips which won't be recognized by paper scanning processes if there is a bit more data in it (happens as soon as Ultimate Creditor will be enabled). And, just as a note, printers and their drivers could get into their bleeding edges too with 997 characters, the specified physical size of the QR code on a OR bill and Error Correction Level "H". So keep it to "M" and you (and your customers) don't get into troubles.
- **Postal Adresses are important*** While a Name and the Country is enough for paying a payment directly with your banking account, it's not enough for sending a letter to someone via snail mail if you having troubles with the payment slip. Swiss QR bill is still a paper based product. You still can pay it with cash in postal offices whereas nobody really knows who you are, which is the reason why Town and Postal Code is mandatory too in structured address. If there would be only Names on the payment slip, chances are very low that a bank can contact anybody afterwards if the payment can't be processed and will be rejected. That's the reason why Name, Postal Code and Town are mandatory fields on a swiss QR bill with structured address (basically that's also mandatory for the combined adress, altough you can't check that automatically). Real world example: If the Creditor Account at the creditors bank is not reachable and post has to pay back the money to the Ultimate Debtor, the postal office needs the address from the Ultimate Debtor. That's the reason why a swiss QR bill will be rejected if the address is not complete enough on the payment slip.
- **Use CR/LF or LF according to the implementation guide.** CR (only) payment data can run into troubles. Not all banks can accept that on all of their channels. Even if they do accept it on one channel (e.g. e-banking), you can't be sure they'll accept it on all other channels too. I know, the FAQ on https://www.paymentstandards.ch/ tells you a different story than the Implementation Guide. But in case of doubt the Implementation Guide overrules the FAQ (trust me).
- **Don't crop the borders of the QR code while printing.** If you loose parts of the three Position Markers, it won't be recognized by most scanners (e.g. https://zxing.org/). While the rest has a good protection against such problems, the Position Markers identify the QR code borders itself and are very important. That's also the reason why they've an eye-catching bigger size compared to the Alignment Markers and Data Points.
- **use UTF-8 for the data and use only allowed characters according to the Implementation Guildeline.** Very important. Banks *do* reject payments if there are not allowed characters in it due to compliance reasons.
- **Mirrored QR Codes won't be recognized by all QR scanners.** (mirror, mirror on the wall...) It seems as there are QR code generators (or PDF engines?!) which produce mirrored QR codes. I'm not quite sure why that happens but it seems to be a fact. As this is not a valid qr code according to the qr standard, not all scanner do recognize them (and they're even more hard to recognize for humans if you rotate them into the right position ;-). https://zxing.org/ do recognize them anyway while https://boofcv.org/ doesn't. As you can guess, alot use one of these libraries (not only banks but also insurance companies, account management softwares and so on). So check your produced QR code with boofcv and zxing. If one of them fails, try a different qr code generator.

Don't kill the messenger, please.
