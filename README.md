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
