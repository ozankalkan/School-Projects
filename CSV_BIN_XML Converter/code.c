#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <libxml/parser.h>
#include <libxml/tree.h>
#include <libxml/xmlschemastypes.h>

//This struct is for keeping the data of records of files. Because of null pointer every char array's size is incremented by one.
struct records
{
    char name[21];
    char surname[31];
    char gender;
    char occupancy[31];
    char level_of_education[4];
    char email[50];
    char bank_account_number[13];
    char IBAN[28];
    char account_type[14];
    char currency_unit[4];
    int total_balance_available;
    char available_for_loan[5];
};

//This line is the front declaration of endianConverter function that we later explain.
float endianConverter(int numberToBeConverted);

int main(int argc, char *argv[])
{
    if (argc != 4)
    {
        printf("Wrong Argument Usage, Correct Usage Will Be: %s <input_file> <output_file> <type>\n", argv[0]);
        return 1;
    }

    //Using typedef to create structs easier.
    typedef struct records Records;

    //Assigning the arguments to a variable.
    char *input_file = argv[1];
    char *output_file = argv[2];
    char *type = argv[3];

    printf("Input File: %s\n", input_file);
    printf("Output File: %s\n", output_file);
    printf("Type: %s\n", type);

    //This if conditions controls what the user wants to do. If type is equal to 1 then user wants to convert a csv file to a binary(dat) file.
    //If type is equal to 2 then user wants to convert a binary(dat) file to a xml file. If type is equal to 3 then user wants to validate the given...
    //...xml file with the given xsd file.
    if (strcmp(type, "1") == 0)
    {

        //Creating a Records object for keeping the data of the given csv file.
        Records recordsForCSV;
        //Opening the given csv file to read it.
        FILE *fpForCSV;
        //Opening the given binary(dat) file to write the contents of the given csv into it. 
        FILE *fpForBinW;
        //Checking the csv file for not to have errors.
        fpForCSV = fopen(input_file, "r");
        if (fpForCSV == NULL)
        {
            puts("An error has occured while opening the given CSV file.");
            exit(1);
        }
        //Checking the binary(dat) file for not to have errors.
        fpForBinW = fopen(output_file, "wb");
        if (fpForBinW == NULL)
        {
            puts("An error has occured while opening the DAT given file.");
            exit(1);
        }

        char *charPointer;
        char lineData[512];
        //Discarding the first line because it has titles.
        fgets(lineData, 512, fpForCSV);
        //This while loop takes data's of the given csv file, copies them to Records object that we created above and writes them to binary(dat) file.
        while (fgets(lineData, 512, fpForCSV) != NULL)
        {
            int j = 0;
            //This while loop calculates the current line's length. 
            while (lineData[j] != '\0')
            {
                j++;
            }

            // If first element(name) is empty write a space " " character to not have any errors in the future.
            if(lineData[0] == ','){
                for (int m = j + 1; m > -1; m--)
                    {
                        lineData[m + 1] = lineData[m];
                    }
                    lineData[0] = ' ';
                    j++;

            }

            // If last element(available for loan) is empty write a space " " character to not have any errors in the future.
            if(lineData[j - 2] == ','){
                for (int m = j + 1 ; m > j - 2 ; m--)
                    {
                        lineData[m + 1] = lineData[m];
                    }
                    lineData[j - 1] = ' ';
                    j++;
            }
                


            //This for iteration controls if there is any empty data in the current line and if there is, places a space " " character to that place for not ...
            //...missing the data while using strtok function.
            for (int k = 0; k < (sizeof(lineData) / sizeof(lineData[0])) - 1; k++)
            {
                char charData1 = lineData[k];
                char charData2 = lineData[k + 1];
                if (charData1 == ',' && charData2 == ',')
                {
                    for (int m = j + 1; m > k; m--)
                    {
                        lineData[m + 1] = lineData[m];
                    }
                    lineData[k + 1] = ' ';
                    j++;
                }
            }

            //This part copies the data that current line contains and holds them in the Records object.
            //The line is split by comma, and each value is assigned to its corresponding place in the record.
            charPointer = strtok(lineData, ",");
            strcpy(recordsForCSV.name, charPointer);

            charPointer = strtok(NULL, ",");
            strcpy(recordsForCSV.surname, charPointer);

            charPointer = strtok(NULL, ",");
            recordsForCSV.gender = *charPointer;

            charPointer = strtok(NULL, ",");
            strcpy(recordsForCSV.occupancy, charPointer);

            charPointer = strtok(NULL, ",");
            strcpy(recordsForCSV.level_of_education, charPointer);

            charPointer = strtok(NULL, ",");
            strcpy(recordsForCSV.email, charPointer);

            charPointer = strtok(NULL, ",");
            strcpy(recordsForCSV.bank_account_number, charPointer);

            charPointer = strtok(NULL, ",");
            strcpy(recordsForCSV.IBAN, charPointer);

            charPointer = strtok(NULL, ",");
            strcpy(recordsForCSV.account_type, charPointer);

            charPointer = strtok(NULL, ",");
            strcpy(recordsForCSV.currency_unit, charPointer);

            charPointer = strtok(NULL, ",");
            recordsForCSV.total_balance_available = atoi(charPointer);

            charPointer = strtok(NULL, "\n");
            strcpy(recordsForCSV.available_for_loan, charPointer);

            //This part contains the codes that we write Records object's data that we take from the given csv file to the given output binary(dat) file.
            //Since the length of the variables in the struct are fixed, the variables are written to the binary file according to their size.
            fwrite(&recordsForCSV.name, sizeof(recordsForCSV.name[0]), sizeof(recordsForCSV.name) / sizeof(recordsForCSV.name[0]), fpForBinW);
            fwrite(&recordsForCSV.surname, sizeof(recordsForCSV.surname[0]), sizeof(recordsForCSV.surname) / sizeof(recordsForCSV.surname[0]), fpForBinW);
            fwrite(&recordsForCSV.gender, sizeof(recordsForCSV.gender), 1, fpForBinW);
            fwrite(&recordsForCSV.occupancy, sizeof(recordsForCSV.occupancy[0]), sizeof(recordsForCSV.occupancy) / sizeof(recordsForCSV.occupancy[0]), fpForBinW);
            fwrite(&recordsForCSV.level_of_education, sizeof(recordsForCSV.level_of_education[0]), sizeof(recordsForCSV.level_of_education) / sizeof(recordsForCSV.level_of_education[0]), fpForBinW);
            fwrite(&recordsForCSV.email, sizeof(recordsForCSV.email[0]), sizeof(recordsForCSV.email) / sizeof(recordsForCSV.email[0]), fpForBinW);
            fwrite(&recordsForCSV.bank_account_number, sizeof(recordsForCSV.bank_account_number[0]), sizeof(recordsForCSV.bank_account_number) / sizeof(recordsForCSV.bank_account_number[0]), fpForBinW);
            fwrite(&recordsForCSV.IBAN, sizeof(recordsForCSV.IBAN[0]), sizeof(recordsForCSV.IBAN) / sizeof(recordsForCSV.IBAN[0]), fpForBinW);
            fwrite(&recordsForCSV.account_type, sizeof(recordsForCSV.account_type[0]), sizeof(recordsForCSV.account_type) / sizeof(recordsForCSV.account_type[0]), fpForBinW);
            fwrite(&recordsForCSV.currency_unit, sizeof(recordsForCSV.currency_unit[0]), sizeof(recordsForCSV.currency_unit) / sizeof(recordsForCSV.currency_unit[0]), fpForBinW);
            fwrite(&recordsForCSV.total_balance_available, sizeof(int), 1, fpForBinW);
            fwrite(&recordsForCSV.available_for_loan, sizeof(recordsForCSV.available_for_loan[0]), sizeof(recordsForCSV.available_for_loan) / sizeof(recordsForCSV.available_for_loan[0]), fpForBinW);
            
        }
        //Closing the files.
        fclose(fpForCSV);
        fclose(fpForBinW);
    }

    else if (strcmp(type, "2") == 0)
    {
        //Opening the given binary(dat) file to count how many lines does it have.
        FILE *fpForLineCount;
        fpForLineCount = fopen(input_file, "rb");
        //Checking the binary(dat) file for not to have errors.
        if (fpForLineCount == NULL)
        {
            puts("An error has occured while opening the given DAT file.");
            exit(1);
        }
        //By placing the pointer at the end of the file, the size of the file is found, 
        //and by dividing this size by the size of the struct, the total number of records is calculated.
        Records recordsForSize;
        fseek(fpForLineCount, 0, SEEK_END);
        int fileSize = ftell(fpForLineCount);
        int total = sizeof(recordsForSize.name) + sizeof(recordsForSize.surname) + sizeof(recordsForSize.gender) + sizeof(recordsForSize.occupancy) + sizeof(recordsForSize.level_of_education) + sizeof(recordsForSize.email) + sizeof(recordsForSize.bank_account_number) + sizeof(recordsForSize.IBAN) + sizeof(recordsForSize.account_type) + sizeof(recordsForSize.currency_unit) + sizeof(recordsForSize.total_balance_available) + sizeof(recordsForSize.available_for_loan);
        int recordsSize = fileSize / total;

        //Creating a Records object for keeping the data of the given binary(dat) file.
        Records recordsForBinary;
        //Opening the given binary(dat) file to read the contents of it. 
        FILE *fpForBinR;
        fpForBinR = fopen(input_file, "rb");
        //Checking the binary(dat) file for not to have errors.
        if (fpForBinR == NULL)
        {
            puts("An error has occured while opening the given file.");
            exit(1);
        }

        //Opening a new xml file.
        xmlDocPtr docX = xmlNewDoc(BAD_CAST "1.0");
        //Setting the root node as records.
        xmlNodePtr rootNode = xmlNewNode(NULL, BAD_CAST "records");
        xmlDocSetRootElement(docX, rootNode);

        char lineIDchar[4];
        int i = 0;
        for (; i < recordsSize; i++)
        {
            //This part copies the data that the binary(dat) file contains and holds them in the Records object.
            //Since the length of the variables in the struct are fixed, the variables are read from the binary file according to their size.
            fread(&recordsForBinary.name, sizeof(recordsForBinary.name[0]), sizeof(recordsForBinary.name) / sizeof(recordsForBinary.name[0]), fpForBinR);
            fread(&recordsForBinary.surname, sizeof(recordsForBinary.surname[0]), sizeof(recordsForBinary.surname) / sizeof(recordsForBinary.surname[0]), fpForBinR);
            fread(&recordsForBinary.gender, sizeof(recordsForBinary.gender), 1, fpForBinR);
            fread(&recordsForBinary.occupancy, sizeof(recordsForBinary.occupancy[0]), sizeof(recordsForBinary.occupancy) / sizeof(recordsForBinary.occupancy[0]), fpForBinR);
            fread(&recordsForBinary.level_of_education, sizeof(recordsForBinary.level_of_education[0]), sizeof(recordsForBinary.level_of_education) / sizeof(recordsForBinary.level_of_education[0]), fpForBinR);
            fread(&recordsForBinary.email, sizeof(recordsForBinary.email[0]), sizeof(recordsForBinary.email) / sizeof(recordsForBinary.email[0]), fpForBinR);
            fread(&recordsForBinary.bank_account_number, sizeof(recordsForBinary.bank_account_number[0]), sizeof(recordsForBinary.bank_account_number) / sizeof(recordsForBinary.bank_account_number[0]), fpForBinR);
            fread(&recordsForBinary.IBAN, sizeof(recordsForBinary.IBAN[0]), sizeof(recordsForBinary.IBAN) / sizeof(recordsForBinary.IBAN[0]), fpForBinR);
            fread(&recordsForBinary.account_type, sizeof(recordsForBinary.account_type[0]), sizeof(recordsForBinary.account_type) / sizeof(recordsForBinary.account_type[0]), fpForBinR);
            fread(&recordsForBinary.currency_unit, sizeof(recordsForBinary.currency_unit[0]), sizeof(recordsForBinary.currency_unit) / sizeof(recordsForBinary.currency_unit[0]), fpForBinR);
            fread(&recordsForBinary.total_balance_available, sizeof(int), 1, fpForBinR);
            fread(&recordsForBinary.available_for_loan, sizeof(recordsForBinary.available_for_loan[0]), sizeof(recordsForBinary.available_for_loan) / sizeof(recordsForBinary.available_for_loan[0]), fpForBinR);
        
            //We convert the integer value that holds the row id to a string value so that we can write it to an xml file.
            sprintf(lineIDchar, "%d", (i + 1));
            //Creating row element, adding the id attribute to it and connecting it to the root node.
            xmlNodePtr rowNode = xmlNewChild(rootNode, NULL, BAD_CAST "row", NULL);
            xmlNewProp(rowNode, BAD_CAST "id", BAD_CAST lineIDchar);
            //Creating customerInfoNode element, and connecting it to the row node.
            xmlNodePtr customerInfoNode = xmlNewChild(rowNode, NULL, BAD_CAST "customer_info", NULL);
            //Creating all nodes before assigning them.
            xmlNodePtr nameNode;
            xmlNodePtr surnameNode;
            xmlNodePtr genderNode;
            xmlNodePtr occupancyNode;
            xmlNodePtr level_of_educationNode;
            xmlNodePtr emailNode;
            xmlNodePtr bankAccountInfoNode;
            xmlNodePtr bank_account_numberNode;
            xmlNodePtr IBANNode;
            xmlNodePtr account_typeNode;
            xmlNodePtr total_balance_availableNode;
            xmlNodePtr available_for_loanNode;

            //If the given data is space " " character then write nothing "" to xml file. Else write the given data to xml.
            //The nodes of this part are all the child node of customerInfoNode.
            if (strcmp(recordsForBinary.name, " ") == 0)
                nameNode = xmlNewChild(customerInfoNode, NULL, BAD_CAST "name", BAD_CAST "");
            else
                nameNode = xmlNewChild(customerInfoNode, NULL, BAD_CAST "name", BAD_CAST recordsForBinary.name);

            if (strcmp(recordsForBinary.surname, " ") == 0)
                surnameNode = xmlNewChild(customerInfoNode, NULL, BAD_CAST "surname", BAD_CAST "");
            else
                surnameNode = xmlNewChild(customerInfoNode, NULL, BAD_CAST "surname", BAD_CAST recordsForBinary.surname);

            //We convert the char value that holds the gender to a string value so that we can write it to an xml file.
            char gender[2];
            sprintf(gender, "%c", recordsForBinary.gender);
            if (strcmp(gender, " ") == 0)
                genderNode = xmlNewChild(customerInfoNode, NULL, BAD_CAST "gender", BAD_CAST "");
            else
                genderNode = xmlNewChild(customerInfoNode, NULL, BAD_CAST "gender", BAD_CAST gender);

            if (strcmp(recordsForBinary.occupancy, " ") == 0)
                occupancyNode = xmlNewChild(customerInfoNode, NULL, BAD_CAST "occupancy", BAD_CAST "");
            else
                occupancyNode = xmlNewChild(customerInfoNode, NULL, BAD_CAST "occupancy", BAD_CAST recordsForBinary.occupancy);

            if (strcmp(recordsForBinary.level_of_education, " ") == 0)
                level_of_educationNode = xmlNewChild(customerInfoNode, NULL, BAD_CAST "level_of_education", BAD_CAST "");
            else
                level_of_educationNode = xmlNewChild(customerInfoNode, NULL, BAD_CAST "level_of_education", BAD_CAST recordsForBinary.level_of_education);

            if (strcmp(recordsForBinary.email, " ") == 0)
                emailNode = xmlNewChild(customerInfoNode, NULL, BAD_CAST "email", BAD_CAST "");
            else
                emailNode = xmlNewChild(customerInfoNode, NULL, BAD_CAST "email", BAD_CAST recordsForBinary.email);

            //Creating bankAccountInfoNode element, and connecting it to the row node.
            bankAccountInfoNode = xmlNewChild(rowNode, NULL, BAD_CAST "bank_account_info", NULL);

            //The nodes of this part are all the child node of bankAccountInfoNode.
            if (strcmp(recordsForBinary.bank_account_number, " ") == 0)
                bank_account_numberNode = xmlNewChild(bankAccountInfoNode, NULL, BAD_CAST "bank_account_number", BAD_CAST "");
            else
                bank_account_numberNode = xmlNewChild(bankAccountInfoNode, NULL, BAD_CAST "bank_account_number", BAD_CAST recordsForBinary.bank_account_number);

            if (strcmp(recordsForBinary.IBAN, " ") == 0)
                IBANNode = xmlNewChild(bankAccountInfoNode, NULL, BAD_CAST "IBAN", BAD_CAST "");
            else
                IBANNode = xmlNewChild(bankAccountInfoNode, NULL, BAD_CAST "IBAN", BAD_CAST recordsForBinary.IBAN);

            if (strcmp(recordsForBinary.account_type, " ") == 0)
                account_typeNode = xmlNewChild(bankAccountInfoNode, NULL, BAD_CAST "account_type", BAD_CAST "");
            else
                account_typeNode = xmlNewChild(bankAccountInfoNode, NULL, BAD_CAST "account_type", BAD_CAST recordsForBinary.account_type);


            char total_balance[15];
            char total_balanceBigEndian[32];
            //We convert the integer value that holds the total balance available to a string value so that we can write it to an xml file.
            sprintf(total_balance, "%d", recordsForBinary.total_balance_available);

            if (strcmp(total_balance, " ") == 0)
            {
                total_balance_availableNode = xmlNewChild(bankAccountInfoNode, NULL, BAD_CAST "total_balance_available", BAD_CAST "");
                if (strcmp(recordsForBinary.currency_unit, " ") == 0)
                    xmlNewProp(total_balance_availableNode, BAD_CAST "currency_unit", BAD_CAST "");
                else
                    xmlNewProp(total_balance_availableNode, BAD_CAST "currency_unit", BAD_CAST recordsForBinary.currency_unit);

                xmlNewProp(total_balance_availableNode, BAD_CAST "bigEnd_Version", BAD_CAST "");
            }
            else
            {
                total_balance_availableNode = xmlNewChild(bankAccountInfoNode, NULL, BAD_CAST "total_balance_available", BAD_CAST total_balance);
                if (strcmp(recordsForBinary.currency_unit, " ") == 0)
                    xmlNewProp(total_balance_availableNode, BAD_CAST "currency_unit", BAD_CAST "");
                else
                    xmlNewProp(total_balance_availableNode, BAD_CAST "currency_unit", BAD_CAST recordsForBinary.currency_unit);
                
                //If total balance is not empty, we convert the integer value that holds the big endian version of the total balance available to a string value so that we can write it to an xml file.
                sprintf(total_balanceBigEndian, "%.0f", endianConverter(recordsForBinary.total_balance_available));
                xmlNewProp(total_balance_availableNode, BAD_CAST "bigEnd_Version", BAD_CAST total_balanceBigEndian);
            }

            if (strcmp(recordsForBinary.available_for_loan, " ") == 0)
                available_for_loanNode = xmlNewChild(bankAccountInfoNode, NULL, BAD_CAST "available_for_loan", BAD_CAST "");
            else
                available_for_loanNode = xmlNewChild(bankAccountInfoNode, NULL, BAD_CAST "available_for_loan", BAD_CAST recordsForBinary.available_for_loan);
        
        
        }
        //Closing the binary file.
        fclose(fpForBinR);

        //Saving the xml file that we filled above.
        xmlSaveFormatFileEnc(output_file, docX, "UTF-8", 1);
        xmlFreeDoc(docX);
    }

    else if (strcmp(type, "3") == 0)
    {
        xmlDocPtr doc;
        xmlSchemaPtr schema = NULL;
        xmlSchemaParserCtxtPtr ctxt;

        char *XMLFileName = input_file;  // xml file 
        char *XSDFileName = output_file; // xsd file 

        xmlLineNumbersDefault(1);                   // set line numbers, 0> no substitution, 1>substitution
        ctxt = xmlSchemaNewParserCtxt(XSDFileName); // create an xml schemas parse context
        schema = xmlSchemaParse(ctxt);              // parse a schema definition resource and build an internal XML schema
        xmlSchemaFreeParserCtxt(ctxt);              // free the resources associated to the schema parser context

        doc = xmlReadFile(XMLFileName, NULL, 0); // parse an XML file
        if (doc == NULL)
        {
            fprintf(stderr, "Could not parse %s\n", XMLFileName);
        }
        else
        {
            xmlSchemaValidCtxtPtr ctxt; // structure xmlSchemaValidCtxt, not public by API
            int ret;

            ctxt = xmlSchemaNewValidCtxt(schema);  // create an xml schemas validation context
            ret = xmlSchemaValidateDoc(ctxt, doc); // validate a document tree in memory
            if (ret == 0)                          // validated
            {
                printf("%s validates\n", XMLFileName);
            }
            else if (ret > 0) // positive error code number
            {
                printf("%s fails to validate\n", XMLFileName);
            }
            else // internal or API error
            {
                printf("%s validation generated an internal error\n", XMLFileName);
            }
            xmlSchemaFreeValidCtxt(ctxt); // free the resources associated to the schema validation context
            xmlFreeDoc(doc);
        }
        // free the resource
        if (schema != NULL)
            xmlSchemaFree(schema); // deallocate a schema structure

        xmlSchemaCleanupTypes(); // cleanup the default xml schemas types library
        xmlCleanupParser();      // cleans memory allocated by the library itself
        xmlMemoryDump();         // memory dump
    }
    else{ //If the entered type is invalid, a warning message is printed to the user.
        printf("Invalid type! \n");
    }
        

    return 0;
}

float endianConverter(int numberToBeConverted)
{
    //The array, which will hold 32 integers, is opened with the calloc function so that each element becomes zero.
    int *arrayPointer = (int *)calloc(32, sizeof(int));
    //A new pointer is created to avoid losing the address of the first element of the array.
    int *arrayPointerStart = arrayPointer;

    //To find Big endian verison we first need to convert the decimal form of the given integer to binary form.
    //As long as the decimal number to be converted to binary form is greater than zero, the binary form is found by calculating...
    //... the mode according to two and dividing by two at each step.
    while (numberToBeConverted > 0)
    {
        *arrayPointer = numberToBeConverted % 2;
        numberToBeConverted = numberToBeConverted / 2;
        arrayPointer = arrayPointer + 1;
    }
    //Finding the last byte's starting point
    int *arrayPointerByteStart = arrayPointerStart + 24;
    int reversedForm[32];

    for (int j = 1; j < 5; j++)
    {
        //This for iteration is for traversing the current byte to last bit of it and writing its bits to reversed form array.
        for (int i = 0 + ((j - 1) * 8); i < 8 * j; i++)
        {
            reversedForm[i] = *arrayPointerByteStart;
            arrayPointerByteStart += 1;
        }
        //Updating the pointer with previous byte's starting point.
        if (j < 4)
        {
            arrayPointerByteStart = arrayPointerByteStart - 16;
        }
    }
    //Free the array.
    free(arrayPointerStart);

    //This part calculates the decimal form of the reversed binary form .
    float result = 0;
    for (short i = 0; i < 32; i++)
    {
        //To calculate the digit value in base 2.
        float mul = 1;
        for (short j = 0; j < i; j++)
        {
            mul = mul * 2;
        }
        //The digit value and the value in binary form(0, 1) are multiplied and added to the result.
        result += reversedForm[i] * mul;
    }

    return result;
}