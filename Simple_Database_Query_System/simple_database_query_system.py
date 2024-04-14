# -*- coding: utf-8 -*-
#2020510044 - Ozan Kalkan
#2021510012 - Hasan Balıkçi

import csv
import json


def delConditions(index1, whileCounter, columnIndex, splittedCode, studentsData):
    if(splittedCode[index1] == "="):
        if(studentsData[whileCounter][columnIndex].upper() == splittedCode[index1+1]):
            del(studentsData[whileCounter])
            whileCounter -= 1
    elif(splittedCode[index1] == "!="):
        if(studentsData[whileCounter][columnIndex].upper() != splittedCode[index1+1]):
            del(studentsData[whileCounter])
            whileCounter -= 1
    elif(splittedCode[index1] == "<"):
        if(int(studentsData[whileCounter][columnIndex]) < int(splittedCode[index1+1])):
            del(studentsData[whileCounter])
            whileCounter -= 1
    elif(splittedCode[index1] == ">"):
        if(int(studentsData[whileCounter][columnIndex]) > int(splittedCode[index1+1])):
            del(studentsData[whileCounter])
            whileCounter -= 1
    elif(splittedCode[index1] == "<="):
        if(int(studentsData[whileCounter][columnIndex]) <= int(splittedCode[index1+1])):
            del(studentsData[whileCounter])
            whileCounter -= 1
    elif(splittedCode[index1] == ">="):
        if(int(studentsData[whileCounter][columnIndex]) >= int(splittedCode[index1+1])):
            del(studentsData[whileCounter])
            whileCounter -= 1
    elif(splittedCode[index1] == "!<"):
        if(int(studentsData[whileCounter][columnIndex]) >= int(splittedCode[index1+1])):
            del(studentsData[whileCounter])
            whileCounter -= 1
    elif(splittedCode[index1] == "!>"):
        if(int(studentsData[whileCounter][columnIndex]) <= int(splittedCode[index1+1])):
            del(studentsData[whileCounter])
            whileCounter -= 1
    return whileCounter


def selConditions(index1, whileCounter, columnIndex, splittedCode, studentsData, selectedData, selectedColumnIndex):
    if(splittedCode[index1] == "="):
        if(studentsData[whileCounter][columnIndex].upper() == splittedCode[index1+1]):
            selectedRow = []
            for selected in range(0,len(selectedColumnIndex)):
                selectedRow.append(studentsData[whileCounter][selectedColumnIndex[selected]])
            selectedData.append(selectedRow)
    elif(splittedCode[index1] == "!="):
        if(studentsData[whileCounter][columnIndex].upper() != splittedCode[index1+1]):
            selectedRow = []
            for selected in range(0,len(selectedColumnIndex)):
                selectedRow.append(studentsData[whileCounter][selectedColumnIndex[selected]])
            selectedData.append(selectedRow)
    elif(splittedCode[index1] == "<"):
        if(int(studentsData[whileCounter][columnIndex]) < int(splittedCode[index1+1])):
            selectedRow = []
            for selected in range(0,len(selectedColumnIndex)):
                selectedRow.append(studentsData[whileCounter][selectedColumnIndex[selected]])
            selectedData.append(selectedRow)
    elif(splittedCode[index1] == ">"):
        if(int(studentsData[whileCounter][columnIndex]) > int(splittedCode[index1+1])):
            selectedRow = []
            for selected in range(0,len(selectedColumnIndex)):
                selectedRow.append(studentsData[whileCounter][selectedColumnIndex[selected]])
            selectedData.append(selectedRow)
    elif(splittedCode[index1] == "<="):
        if(int(studentsData[whileCounter][columnIndex]) <= int(splittedCode[index1+1])):
            selectedRow = []
            for selected in range(0,len(selectedColumnIndex)):
                selectedRow.append(studentsData[whileCounter][selectedColumnIndex[selected]])
            selectedData.append(selectedRow)
    elif(splittedCode[index1] == ">="):
        if(int(studentsData[whileCounter][columnIndex]) >= int(splittedCode[index1+1])):
            selectedRow = []
            for selected in range(0,len(selectedColumnIndex)):
                selectedRow.append(studentsData[whileCounter][selectedColumnIndex[selected]])
            selectedData.append(selectedRow)
    elif(splittedCode[index1] == "!<"):
        if(int(studentsData[whileCounter][columnIndex]) >= int(splittedCode[index1+1])):
            selectedRow = []
            for selected in range(0,len(selectedColumnIndex)):
                selectedRow.append(studentsData[whileCounter][selectedColumnIndex[selected]])
            selectedData.append(selectedRow)
    elif(splittedCode[index1] == "!>"):
        if(int(studentsData[whileCounter][columnIndex]) <= int(splittedCode[index1+1])):
            selectedRow = []
            for selected in range(0,len(selectedColumnIndex)):
                selectedRow.append(studentsData[whileCounter][selectedColumnIndex[selected]])
            selectedData.append(selectedRow)
    return selectedData


def merge_sort(arr, ascOrDsc):
    if len(arr) <= 1:
        return arr


    mid = len(arr) // 2
    left_half = arr[:mid]
    right_half = arr[mid:]


    left_half = merge_sort(left_half, ascOrDsc)
    right_half = merge_sort(right_half, ascOrDsc)


    return merge(left_half, right_half, ascOrDsc)


def merge(left, right, ascOrDsc):
    merged = []
    left_index = right_index = 0

    
    while left_index < len(left) and right_index < len(right):     
        if(ascOrDsc == "ASC"):
            
            if(left[left_index][0].isnumeric()):
                if int(left[left_index][0]) <= int(right[right_index][0]):
                    merged.append(left[left_index])
                    left_index += 1
                else:
                    merged.append(right[right_index])
                    right_index += 1
            else:
                if left[left_index][0] <= right[right_index][0]:
                    merged.append(left[left_index])
                    left_index += 1
                else:
                    merged.append(right[right_index])
                    right_index += 1
            
            
        else:
            if(left[left_index][0].isnumeric()):
                if int(left[left_index][0]) > int(right[right_index][0]):
                    merged.append(left[left_index])
                    left_index += 1
                else:
                    merged.append(right[right_index])
                    right_index += 1
            else:
                if left[left_index][0] > right[right_index][0]:
                    merged.append(left[left_index])
                    left_index += 1
                else:
                    merged.append(right[right_index])
                    right_index += 1
              

    merged.extend(left[left_index:])
    merged.extend(right[right_index:])

    return merged



studentsData = []

file = open('students.csv','r')
readerForCSV = csv.reader(file)

for row in readerForCSV:
    studentsData.append(row)

for i in range(0, len(studentsData)):
    studentsData[i] = studentsData[i][0].split(";")
    
columns = studentsData[0]

for columnsFor in range(0,len(columns)):
    columns[columnsFor] = columns[columnsFor].upper()

studentsData = merge_sort(studentsData[1:], "ASC")


conditions = {"=", "!=", "<", ">", "<=", ">=", "!<", "!>"}
while True:
    code = input("\nEnter your query: ")
    codeForData = code
    code = code.upper()
    splittedCode = code.split(" ")
    flag = True
    if splittedCode[0] == "SELECT" :
        
        if len(splittedCode) != 11 and len(splittedCode) != 15:
            flag = False
        elif splittedCode[1] != "ALL" :
            if splittedCode[1].count(",") > 0:
                columnsSplitted = splittedCode[1].split(",")
                for i in range(0, len(columnsSplitted)):
                    if columnsSplitted[i] not in columns:
                        flag = False
                        break
            elif splittedCode[1] not in columns :
                flag = False
        elif splittedCode[2] != "FROM" :
            flag = False
        elif splittedCode[3] != "STUDENTS" :
            flag = False
        elif splittedCode[4] != "WHERE" :
            flag = False
        elif splittedCode[5] not in columns :
            flag = False
        elif splittedCode[6] not in conditions :
            flag = False
        if (len(splittedCode) == 11 or len(splittedCode) == 15) and splittedCode[5] in columns and splittedCode[6] in conditions:
            if splittedCode[5] == "NAME" :
                if splittedCode[7].isnumeric() :
                    flag = False
                elif splittedCode[7][0] != '"' :
                    flag = False
                elif splittedCode[7][-1] != '"':
                    flag = False
                elif splittedCode[6] != "=" and splittedCode[6] != "!=" :
                    flag = False
            elif splittedCode[5] == "LASTNAME" :
                if splittedCode[7].isnumeric():
                    flag = False
                elif splittedCode[7][0] != '"' :
                    flag = False
                elif splittedCode[7][-1] != '"':
                    flag = False
                elif splittedCode[6] != "=" and splittedCode[6] != "!=" :
                    flag = False
            elif splittedCode[5] == "ID" :
                if splittedCode[7].isnumeric() == False :
                    flag = False
            elif splittedCode[5] == "EMAIL" :
                if splittedCode[7][1] == "@" or splittedCode[7].count("@") != 1 or splittedCode[7][-5] != "." or splittedCode[7][-1] != '"' or splittedCode[7][0] != '"':  
                    flag = False
                elif splittedCode[6] != "=" and splittedCode[6] != "!=" :
                    flag = False
            elif splittedCode[5] == "GRADE" :
                if splittedCode[7].isnumeric() == False :
                    flag = False
                elif splittedCode[7].isnumeric() :
                    if int(splittedCode[7]) > 100 or int(splittedCode[7]) < 0 :
                        flag = False
        if len(splittedCode) == 15:   
            if splittedCode[8] == "AND" or splittedCode[8] == "OR" :
                if splittedCode[9] not in columns :
                    flag = False
                elif splittedCode[10] not in conditions :
                    flag = False
                if splittedCode[9] in columns and splittedCode[10] in conditions:
                    if splittedCode[9] == "NAME" :
                        if splittedCode[11].isnumeric() :
                            flag = False
                        elif splittedCode[11][0] != '"' :
                            flag = False
                        elif splittedCode[11][-1] != '"':
                            flag = False
                        elif splittedCode[10] != "=" and splittedCode[10] != "!=" :
                            flag = False
                    elif splittedCode[9] == "LASTNAME" :
                        if splittedCode[11].isnumeric():
                            flag = False
                        elif splittedCode[11][0] != '"' :
                            flag = False
                        elif splittedCode[11][-1] != '"':
                            flag = False
                        elif splittedCode[10] != "=" and splittedCode[10] != "!=" :
                            flag = False
                    elif splittedCode[9] == "ID" :
                        if splittedCode[11].isnumeric() == False :
                            flag = False
                    elif splittedCode[9] == "EMAIL" :
                        if splittedCode[11][1] == "@" or splittedCode[11].count("@") != 1 or splittedCode[11][-5] != "." or splittedCode[11][-1] != '"' or splittedCode[11][0] != '"':  
                            flag = False
                        elif splittedCode[10] != "=" and splittedCode[10] != "!=" :
                            flag = False
                    elif splittedCode[9] == "GRADE" :
                        if splittedCode[11].isnumeric() == False :
                            flag = False
                        elif splittedCode[11].isnumeric() :
                            if int(splittedCode[11]) > 100 or int(splittedCode[11]) < 0 :
                                flag = False  
                if splittedCode[12] != "ORDER" :
                    flag = False
                elif splittedCode[13] != "BY" :
                    flag = False
                elif splittedCode[14] != "ASC" and splittedCode[14] != "DSC" :
                    flag = False
            if len(splittedCode) == 11 :
                if splittedCode[8] != "AND" and splittedCode[8] != "OR" :
                    if splittedCode[8] != "ORDER" :
                        flag = False
                    elif splittedCode[9] != "BY" :
                        flag = False
                    elif splittedCode[10] != "ASC" and splittedCode[10] != "DSC" :
                        flag = False
        
            
        if flag == False:
            print("Invalid Query")
        else:
            
            if(len(studentsData) == 0):
                print("The table is currently empty. You can insert some values if you want.")
            
            selectedColumns = []
            indexOfSelectedColumns = []
            
            if(splittedCode[1] != "ALL"):
                if "," in splittedCode[1]:
                    selectedColumns = splittedCode[1].split(",")
                else:
                    selectedColumns.append(splittedCode[1])
            
            else:
                selectedColumns = columns
                
            for i in range(0, len(selectedColumns)):
                for j in range(0, len(columns)):
                    if(selectedColumns[i] == columns[j]):
                        indexOfSelectedColumns.append(j)
            
            if (len(splittedCode) == 11):
                
                if(splittedCode[5] != "ID" and splittedCode[5] != "GRADE"):
                   splittedCode[7] = splittedCode[7][1:-1]
                
                columnIndex = 0;
                for i in range(0, len(columns)):
                    if(splittedCode[5] == columns[i]):
                        columnIndex = i
                        
                whileCounter = 0;
                newSelectedData = []
                while(whileCounter<len(studentsData)):
                    newSelectedData = selConditions(6, whileCounter, columnIndex, splittedCode, studentsData, newSelectedData, indexOfSelectedColumns)
                    whileCounter += 1
                    
                newSelectedData = merge_sort(newSelectedData, splittedCode[10])
                
                for i in range(0,len(selectedColumns)):
                    if(selectedColumns[i] != "EMAIL"):
                        print("{:<15}".format(selectedColumns[i]), end="")
                    else:
                        print("{:<35}".format(selectedColumns[i]), end="")
                    
                print()
                for i in range(0,len(newSelectedData)):
                    for j in range(0, len(newSelectedData[i])):
                        if "@" not in newSelectedData[i][j]:
                            print("{:15}".format(newSelectedData[i][j]), end="")
                        else:
                            print("{:35}".format(newSelectedData[i][j]), end="")
                    print()
 
            else:
                if(splittedCode[9] != "ID" and splittedCode[9] != "GRADE"):
                   splittedCode[11] = splittedCode[11][1:-1]
                if(splittedCode[5] != "ID" and splittedCode[5] != "GRADE"):
                   splittedCode[7] = splittedCode[7][1:-1]

                columnIndex = 0;
                for i in range(0, len(columns)):
                    if(splittedCode[5] == columns[i]):
                        columnIndex = i
                        
                columnIndex2 = 0;
                for i in range(0, len(columns)):
                    if(splittedCode[9] == columns[i]):
                        columnIndex2 = i
                        
                whileCounter = 0;
                newSelectedData = []
                
                if splittedCode[8] == "AND":
                    
                    while(whileCounter<len(studentsData)):

                        if(splittedCode[6] == "="):
                            if(studentsData[whileCounter][columnIndex].upper() == splittedCode[7]):
                                newSelectedData = selConditions(10, whileCounter, columnIndex2, splittedCode, studentsData, newSelectedData, indexOfSelectedColumns)
                        elif(splittedCode[6] == "!="):
                            if(studentsData[whileCounter][columnIndex].upper() != splittedCode[7]):
                                newSelectedData = selConditions(10, whileCounter, columnIndex2, splittedCode, studentsData, newSelectedData, indexOfSelectedColumns)
                        elif(splittedCode[6] == "<"):
                            if(int(studentsData[whileCounter][columnIndex]) < int(splittedCode[7])):
                                newSelectedData = selConditions(10, whileCounter, columnIndex2, splittedCode, studentsData, newSelectedData, indexOfSelectedColumns)
                        elif(splittedCode[6] == ">"):
                            if(int(studentsData[whileCounter][columnIndex]) > int(splittedCode[7])):
                                newSelectedData = selConditions(10, whileCounter, columnIndex2, splittedCode, studentsData, newSelectedData, indexOfSelectedColumns)
                        elif(splittedCode[6] == "<="):
                            if(int(studentsData[whileCounter][columnIndex]) <= int(splittedCode[7])):
                                newSelectedData = selConditions(10, whileCounter, columnIndex2, splittedCode, studentsData, newSelectedData, indexOfSelectedColumns)
                        elif(splittedCode[6] == ">="):
                            if(int(studentsData[whileCounter][columnIndex]) >= int(splittedCode[7])):
                                newSelectedData = selConditions(10, whileCounter, columnIndex2, splittedCode, studentsData, newSelectedData, indexOfSelectedColumns)
                        elif(splittedCode[6] == "!<"):
                            if(int(studentsData[whileCounter][columnIndex]) >= int(splittedCode[7])):
                                newSelectedData = selConditions(10, whileCounter, columnIndex2, splittedCode, studentsData, newSelectedData, indexOfSelectedColumns)
                        elif(splittedCode[6] == "!>"):
                            if(int(studentsData[whileCounter][columnIndex]) <= int(splittedCode[7])):
                                newSelectedData = selConditions(10, whileCounter, columnIndex2, splittedCode, studentsData, newSelectedData, indexOfSelectedColumns)
            
                        whileCounter += 1
                        
                    newSelectedData = merge_sort(newSelectedData, splittedCode[14])

                    for i in range(0,len(selectedColumns)):
                        if(selectedColumns[i] != "EMAIL"):
                            print("{:<15}".format(selectedColumns[i]), end="")
                        else:
                            print("{:<35}".format(selectedColumns[i]), end="")
                        
                    print()
                    for i in range(0,len(newSelectedData)):
                        for j in range(0, len(newSelectedData[i])):
                            if "@" not in newSelectedData[i][j]:
                                print("{:15}".format(newSelectedData[i][j]), end="")
                            else:
                                print("{:35}".format(newSelectedData[i][j]), end="")
                        print()
    
                else:
                    while(whileCounter<len(studentsData)):
                        dataForControl = newSelectedData
                        newSelectedData = selConditions(6, whileCounter, columnIndex, splittedCode, studentsData, newSelectedData, indexOfSelectedColumns)
                        if (newSelectedData == dataForControl):
                            newSelectedData = selConditions(10, whileCounter, columnIndex2, splittedCode, studentsData, newSelectedData, indexOfSelectedColumns)
                        whileCounter += 1
                        
                    newSelectedData = merge_sort(newSelectedData, splittedCode[14])

                    for i in range(0,len(selectedColumns)):
                        if(selectedColumns[i] != "EMAIL"):
                            print("{:<15}".format(selectedColumns[i]), end="")
                        else:
                            print("{:<35}".format(selectedColumns[i]), end="")
 
                    print()
                    for i in range(0,len(newSelectedData)):
                        for j in range(0, len(newSelectedData[i])):
                            if "@" not in newSelectedData[i][j]:
                                print("{:15}".format(newSelectedData[i][j]), end="")
                            else:
                                print("{:35}".format(newSelectedData[i][j]), end="")
                        print()
            
    elif splittedCode[0] == "INSERT" :
        if len(splittedCode) != 4:
            flag = False
        elif splittedCode[1] != "INTO":
            flag = False
        elif splittedCode[2] != "STUDENTS" :
            flag = False
        elif splittedCode[3][0:6] != "VALUES" :
            flag = False
        elif splittedCode[3][0:6] == "VALUES" :
            if splittedCode[3][6] != "(" :
                flag = False
            elif splittedCode[3][-1] != ")" :
                flag = False
            elif len(splittedCode[3]) != 0:
                values = splittedCode[3][7:-1]
                splittedValues = values.split(",")
                if(len(splittedValues) != len(columns)):
                    flag =False
                elif splittedValues[0].isnumeric() == False:
                    flag = False
                elif splittedValues[1].isnumeric():
                    flag = False
                elif splittedValues[2].isnumeric() :
                    flag = False
                elif splittedValues[3].isnumeric() or splittedValues[3].count("@") != 1 or splittedValues[3][-4] != "." or splittedValues[3][0] == "@":
                    flag = False
                elif splittedValues[4].isnumeric() == False :
                    flag = False
                elif splittedValues[4].isnumeric() :
                    if int(splittedValues[4]) < 0 or int(splittedValues[4]) > 100 :
                        flag = False
        
        if flag == False:
             print("Invalid Query")
        else:
            splittedCodeForData = codeForData.split(" ") 
            values = splittedCodeForData[3][7:-1]
            splittedValues = values.split(",")
            
            flag2 = True
            for i in range(0,len(studentsData)):
                if(studentsData[i][0] == splittedValues[0]):
                    flag2 = False
                    break
            
            if(flag2):
                studentsData.append(splittedValues)
                for i in range(0,len(columns)):
                    if(columns[i] != "EMAIL"):
                        print("{:<15}".format(columns[i]), end="")
                    else:
                        print("{:<35}".format(columns[i]), end="")
                     
                print()
                for i in range(0,len(studentsData)):
                    for j in range(0, len(studentsData[i])):
                        if "@" not in studentsData[i][j]:
                            print("{:15}".format(studentsData[i][j]), end="")
                        else:
                            print("{:35}".format(studentsData[i][j]), end="")
                    print()
            else:
                print("The ID you entered is not a uniqe ID and already exists in the database.")
                   
    elif splittedCode[0] == "DELETE" :
        if len(splittedCode) != 7 and len(splittedCode) != 11:
            flag = False
        elif splittedCode[1] != "FROM" :
            flag = False
        elif splittedCode[2] != "STUDENTS" :
            flag = False
        elif splittedCode[3] != "WHERE" :
            flag = False
        elif splittedCode[4] not in columns :
            flag = False
        elif splittedCode[5] not in conditions :
            flag = False
        if (len(splittedCode) == 7 or len(splittedCode) == 11) and splittedCode[4] in columns and splittedCode[5] in conditions:
            if splittedCode[4] == "NAME" :
                if splittedCode[6].isnumeric() :
                    flag = False
                elif splittedCode[6][0] != '"' :
                    flag = False
                elif splittedCode[6][-1] != '"':
                    flag = False
                elif splittedCode[5] != "=" and splittedCode[5] != "!=" :
                    flag = False 
            elif splittedCode[4] == "LASTNAME" :
                if splittedCode[6].isnumeric():
                    flag = False
                elif splittedCode[6][0] != '"' :
                    flag = False
                elif splittedCode[6][-1] != '"':
                    flag = False
                elif splittedCode[5] != "=" and splittedCode[5] != "!=" :
                    flag = False
            elif splittedCode[4] == "ID" :
                if splittedCode[6].isnumeric() == False :
                    flag = False
            elif splittedCode[4] == "EMAIL" :
                if splittedCode[6][1] == "@" or splittedCode[6].count("@") != 1 or splittedCode[6][-5] != "." or splittedCode[6][-1] != '"' or splittedCode[6][0] != '"':  
                    flag = False
                elif splittedCode[5] != "=" and splittedCode[5] != "!=" :
                    flag = False
            elif splittedCode[4] == "GRADE" :
                if splittedCode[6].isnumeric() == False :
                    flag = False
                elif splittedCode[6].isnumeric() :
                    if int(splittedCode[6]) > 100 or int(splittedCode[6]) < 0 :
                        flag = False
        if len(splittedCode) == 11:
            if splittedCode[7] != "AND" and splittedCode[7] != "OR":
                flag = False
            elif splittedCode[8] not in columns:
                flag = False
            elif splittedCode[9] not in conditions:
                flag = False
            elif splittedCode[8] in columns and splittedCode[9] in conditions:
                if splittedCode[8] == "NAME" :
                    if splittedCode[10].isnumeric() :
                        flag = False
                    elif splittedCode[10][0] != '"' :
                        flag = False
                    elif splittedCode[10][-1] != '"':
                        flag = False
                    elif splittedCode[9] != "=" and splittedCode[9] != "!=" :
                        flag = False
                elif splittedCode[8] == "LASTNAME" :
                    if splittedCode[10].isnumeric():
                        flag = False
                    elif splittedCode[10][0] != '"' :
                        flag = False
                    elif splittedCode[10][-1] != '"':
                        flag = False
                    elif splittedCode[9] != "=" and splittedCode[9] != "!=" :
                        flag = False
                elif splittedCode[8] == "ID" :
                    if splittedCode[10].isnumeric() == False :
                        flag = False
                elif splittedCode[8] == "EMAIL" :
                    if splittedCode[10][1] == "@" or splittedCode[10].count("@") != 1 or splittedCode[10][-5] != "." or splittedCode[10][-1] != '"' or splittedCode[10][0] != '"':  
                        flag = False
                    elif splittedCode[9] != "=" and splittedCode[9] != "!=" :
                        flag = False
                elif splittedCode[8] == "GRADE" :
                    if splittedCode[10].isnumeric() == False :
                        flag = False
                    elif splittedCode[10].isnumeric() :
                        if int(splittedCode[10]) > 100 or int(splittedCode[10]) < 0 :
                            flag = False
            
        
        if flag == False:
             print("Invalid Query")
        else:
             if(len(studentsData) == 0):
                 print("The table is currently empty. You can insert some values if you want.")

             if len(splittedCode) == 7:
                 if(splittedCode[4] != "ID" and splittedCode[4] != "GRADE"):
                    splittedCode[6] = splittedCode[6][1:-1]
                 columnIndex = 0
                 for i in range(0, len(columns)):
                     if(splittedCode[4] == columns[i]):
                         columnIndex = i

                 whileCounter = 0
                 while(whileCounter<len(studentsData)):
                     
                     whileCounter = delConditions(5, whileCounter, columnIndex, splittedCode, studentsData)
                     whileCounter += 1
 
                 for i in range(0,len(columns)):
                     if(columns[i] != "EMAIL"):
                         print("{:<15}".format(columns[i]), end="")
                     else:
                         print("{:<35}".format(columns[i]), end="")
                     
                 print()
                 for i in range(0,len(studentsData)):
                     for j in range(0, len(studentsData[i])):
                         if "@" not in studentsData[i][j]:
                             print("{:15}".format(studentsData[i][j]), end="")
                         else:
                             print("{:35}".format(studentsData[i][j]), end="")
                     print()
                    
             else:
                 if(splittedCode[8] != "ID" and splittedCode[8] != "GRADE"):
                    splittedCode[10] = splittedCode[10][1:-1]
                 if(splittedCode[4] != "ID" and splittedCode[4] != "GRADE"):
                    splittedCode[6] = splittedCode[6][1:-1]
                 columnIndex = 0
                 whileCounter = 0
                 for i in range(0, len(columns)):
                     if(splittedCode[4] == columns[i]):
                         columnIndex = i
                
                 columnIndex2 = 0
                 for j in range(0, len(columns)):
                     if(splittedCode[8] == columns[j]):
                         columnIndex2 = j
                       

                 if (splittedCode[7] == "AND"):
                     
                     while(whileCounter<len(studentsData)):
                         if(splittedCode[5] == "="):
                             if(studentsData[whileCounter][columnIndex].upper() == splittedCode[6]):
                                 whileCounter = delConditions(9, whileCounter, columnIndex2, splittedCode, studentsData)
                         elif(splittedCode[5] == "!="):
                             if(studentsData[whileCounter][columnIndex].upper() != splittedCode[6]):
                                 whileCounter = delConditions(9, whileCounter, columnIndex2, splittedCode, studentsData)
                         elif(splittedCode[5] == "<"):
                             if(int(studentsData[whileCounter][columnIndex]) < int(splittedCode[6])):
                                 whileCounter = delConditions(9, whileCounter, columnIndex2, splittedCode, studentsData)
                         elif(splittedCode[5] == ">"):
                             if(int(studentsData[whileCounter][columnIndex]) > int(splittedCode[6])):
                                 whileCounter = delConditions(9, whileCounter, columnIndex2, splittedCode, studentsData)
                         elif(splittedCode[5] == "<="):
                             if(int(studentsData[whileCounter][columnIndex]) <= int(splittedCode[6])):
                                 whileCounter = delConditions(9, whileCounter, columnIndex2, splittedCode, studentsData)
                         elif(splittedCode[5] == ">="):
                             if(int(studentsData[whileCounter][columnIndex]) >= int(splittedCode[6])):
                                 whileCounter = delConditions(9, whileCounter, columnIndex2, splittedCode, studentsData)
                         elif(splittedCode[5] == "!<"):
                             if(int(studentsData[whileCounter][columnIndex]) >= int(splittedCode[6])):
                                 whileCounter = delConditions(9, whileCounter, columnIndex2, splittedCode, studentsData)
                         elif(splittedCode[5] == "!>"):
                             if(int(studentsData[whileCounter][columnIndex]) <= int(splittedCode[6])):
                                 whileCounter = delConditions(9, whileCounter, columnIndex2, splittedCode, studentsData)
                         whileCounter += 1
                     
                     for i in range(0,len(columns)):
                         if(columns[i] != "EMAIL"):
                             print("{:<15}".format(columns[i]), end="")
                         else:
                             print("{:<35}".format(columns[i]), end="")

                     print()
                     for i in range(0,len(studentsData)):
                         for j in range(0, len(studentsData[i])):
                             if "@" not in studentsData[i][j]:
                                 print("{:15}".format(studentsData[i][j]), end="")
                             else:
                                 print("{:35}".format(studentsData[i][j]), end="")
                         print()

                 else:
                     
                     while(whileCounter<len(studentsData)):
                         whileCounter = delConditions(5, whileCounter, columnIndex, splittedCode, studentsData)
                         whileCounter = delConditions(9, whileCounter, columnIndex2, splittedCode, studentsData)
                         whileCounter += 1

                     for i in range(0,len(columns)):
                         if(columns[i] != "EMAIL"):
                             print("{:<15}".format(columns[i]), end="")
                         else:
                             print("{:<35}".format(columns[i]), end="")
  
                     print()

                     for i in range(0,len(studentsData)):
                         for j in range(0, len(studentsData[i])):
                             if "@" not in studentsData[i][j]:
                                 print("{:15}".format(studentsData[i][j]), end="")
                             else:
                                 print("{:35}".format(studentsData[i][j]), end="")
                         print()
             

    elif splittedCode[0] == "EXIT" :
        with open ('students.json', 'w') as f:
            studentsData_dct = []
            columnsLower = columns
            for j in range (0, len(columns)):
                columnsLower[j] = columns[j].lower()
            for i in range(0, len(studentsData)):
                studentsData_dct.append(dict(zip(columnsLower, studentsData[i])))

            studentsDict = {"Students":studentsData_dct}
            json.dump(studentsDict, f, indent=4, ensure_ascii=False)
  
        break
    else :
        print("Invalid Query")
        