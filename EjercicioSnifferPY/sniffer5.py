
import socket, sys
from struct import *
 
#Convertidor a string
def eth_addr (a) :
  b = "%.2x:%.2x:%.2x:%.2x:%.2x:%.2x" % (ord(a[0]) , ord(a[1]) , ord(a[2]), ord(a[3]), ord(a[4]) , ord(a[5]))
  return b
 

try:
    s = socket.socket( socket.AF_PACKET , socket.SOCK_RAW , socket.ntohs(0x0003))
except socket.error , msg:
    print 'Socket could not be created. Error Code : ' + str(msg[0]) + ' Message ' + msg[1]
    sys.exit()
 
# receive a packet
while True:
    packet = s.recvfrom(65565)
     
    #packet string from tuple
    packet = packet[0]
     
    #parse ethernet header
    eth_length = 14
     
    eth_header = packet[:eth_length]
    eth = unpack('!6s6sH' , eth_header)
    eth_protocol = socket.ntohs(eth[2])
    print 'Nombre: ALANOCA VALERO ARNOLD RICHTER  CI:9073006 LP'
    print 'Ethernet Header'
    print '|-Destination MAC : ' + eth_addr(packet[0:6])
    print '|-Source MAC : ' + eth_addr(packet[6:12])
    print '|-Protocol : ' + str(eth_protocol)
    print ''
    #Parse IP packets, IP Protocol number = 8
    if eth_protocol == 8 :
        #Parse IP header
        #take first 20 characters for the ip header
        ip_header = packet[eth_length:20+eth_length]
         
        #now unpack them :)
        iph = unpack('!BBHHHBBH4s4s' , ip_header)
 
        version_ihl = iph[0]
        ip_tos = iph[1]
        ip_len = iph[2]
        ip_id = iph[3]
        version = version_ihl >> 4
        ihl = version_ihl & 0xF
 
        iph_length = ihl * 4
 
        ttl = iph[5]
        protocol = iph[6]

        ip_sum = iph[7]
        s_addr = socket.inet_ntoa(iph[8]);
        d_addr = socket.inet_ntoa(iph[9]);
        print 'Nombre: ALANOCA VALERO ARNOLD RICHTER  CI:9073006 LP'
        print 'IP Header'
        print '|-IP Version : ' + str(version) 
        print '|-IP Header Length : ' , ihl, 'DWORDS or',str(ihl*32//8) ,'bytes'
        print '|-Type of Service (TOS): ',str(ip_tos)
        print '|-IP Total Length: ',ip_len, ' DWORDS ',str(ip_len*32//8) ,'bytes'
        print '|-Identification: ',ip_id
        print '|-TTL : ' + str(ttl)
        print '|-Protocol : ' + str(protocol)
        print '|-Chksum: ',ip_sum 
        print '|-Source IP : ' + str(s_addr) 
        print '|-Destination IP : ' + str(d_addr)
        print ' '
        #TCP protocol
        if protocol == 6 :
            t = iph_length + eth_length
            tcp_header = packet[t:t+20]
 
            #now unpack them :)
            tcph = unpack('!HHLLBBHHH' , tcp_header)
             
            source_port = tcph[0]
            dest_port = tcph[1]
            sequence = tcph[2]
            acknowledgement = tcph[3]
            doff_reserved = tcph[4]
            tcph_length = doff_reserved >> 4
            tcph_flags = tcph[5]
            tcph_window_size = tcph[6]      #uint16_t
            tcph_checksum = tcph[7]         #uint16_t
            tcph_urgent_pointer = tcph[8]
            osf = bin(tcph[4]) [2:].zfill(16)
            urg = osf[10:11]
            ack = osf[11:12]
            psh = osf[12:13]
            rst = osf[13:14]
            syn = osf[14:15]
            fin = osf[15:16]
              
   
            print 'Nombre: ALANOCA VALERO ARNOLD RICHTER  CI:9073006 LP'
            print 'TCP Header' 
            print '|-Source Port : ' + str(source_port)
            print '|-Dest Port : ' + str(dest_port)
            print '|-Sequence Number : ' + str(sequence)
            print '|-Acknowledgement : ' + str(acknowledgement)
            print '|-Header length : ' ,tcph_length,'DWORDS or ',str(tcph_length*32//8) ,'bytes'
            print '|-Urgent          Flag:',str(urg)
            print '|-Acknowledgement Flag:',str(ack)
            print '|-Push            Flag:',str(psh)
            print '|-Reset           Flag:',str(rst)
            print '|-Synchronise     Flag:',str(syn)
            print '|-Finish          Flag:',str(fin)
            print '|-Window Size:',tcph_window_size
            print '|-Checksum:',tcph_checksum
            print '|-Urgent Pointer:',tcph_urgent_pointer
            print '' 
            h_size = eth_length + iph_length + tcph_length * 4
            data_size = len(packet) - h_size
             
            #get data from the packet
            data = packet[h_size:]
             
            #print 'Data : ' + data
 
        #ICMP Packets
        elif protocol == 1 :
            u = iph_length + eth_length
            icmph_length = 4
            icmp_header = packet[u:u+4]
 
            #now unpack them :)
            icmph = unpack('!BBH' , icmp_header)
             
            icmp_type = icmph[0]
            code = icmph[1]
            checksum = icmph[2]
            print 'Nombre: ALANOCA VALERO ARNOLD RICHTER  CI:9073006 LP'
            print 'ICMP Header' 
            print '|-Type : ' + str(icmp_type)
            print '|-Code : ' + str(code)
            print '|-Checksum : ' + str(checksum)
            print '' 
            h_size = eth_length + iph_length + icmph_length
            data_size = len(packet) - h_size
             
            #get data from the packet
            data = packet[h_size:]
             
            #print 'Data : ' + data
 
        #UDP packets
        elif protocol == 17 :
            u = iph_length + eth_length
            udph_length = 8
            udp_header = packet[u:u+8]
 
            #now unpack them :)
            udph = unpack('!HHHH' , udp_header)
             
            source_port = udph[0]
            dest_port = udph[1]
            length = udph[2]
            checksum = udph[3]
            print 'Nombre: ALANOCA VALERO ARNOLD RICHTER  CI:9073006 LP'
            print 'UDP Header' 
            print '|-Source Port : ' + str(source_port)
            print '|-Dest Port : ' + str(dest_port)
            print '|-Length : ' + str(length)
            print '|-Checksum : ' + str(checksum)
            print '' 
            h_size = eth_length + iph_length + udph_length
            data_size = len(packet) - h_size
             
            #get data from the packet
            data = packet[h_size:]
             
            #print 'Data : ' + data
 
        #some other IP packet like IGMP
        else :
            print 'Protocol other than TCP/UDP/ICMP'
             
        print
